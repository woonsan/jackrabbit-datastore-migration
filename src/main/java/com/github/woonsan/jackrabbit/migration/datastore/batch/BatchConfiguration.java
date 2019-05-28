/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.woonsan.jackrabbit.migration.datastore.batch;

import java.io.File;

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.core.data.Backend;
import org.apache.jackrabbit.core.data.CachingDataStoreAccess;
import org.apache.jackrabbit.core.data.DataRecord;
import org.apache.jackrabbit.core.data.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
@ConfigurationProperties(prefix="batch")
public class BatchConfiguration {

    private static Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataStoreFactory dataStoreFactory;

    @Autowired
    private SourceDataStoreConfiguration sourceDataStoreConfiguration;

    @Autowired
    private TargetDataStoreConfiguration targetDataStoreConfiguration;

    private int minWorkers = 10;

    private int maxWorkers = 10;

    public int getMinWorkers() {
        return minWorkers;
    }

    public void setMinWorkers(int minWorkers) {
        this.minWorkers = minWorkers;
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public void setMaxWorkers(int maxWorkers) {
        this.maxWorkers = maxWorkers;
    }

    @Bean
    public DataRecordReader dataRecordReader() throws RepositoryException {
        final DataStore dataStore = dataStoreFactory.getDataStore(sourceDataStoreConfiguration);
        return new DataRecordReader(dataStore);
    }

    @Bean
    public DataRecordWriter dataRecordWriter() throws RepositoryException {
        final DataStore dataStore = dataStoreFactory.getDataStore(targetDataStoreConfiguration);
        final Backend backend = CachingDataStoreAccess.getBackendIfAccessible(dataStore);

        if (!targetDataStoreConfiguration.isDirectBackendAccess() || backend == null) {
            log.info("Creating DataRecordWriter with DataStore.");
            return new DataRecordWriter(dataStore);
        }

        final File backendTempDir;
        final String homeDir = targetDataStoreConfiguration.getHomeDir();

        if (homeDir == null) {
            backendTempDir = new File(System.getProperty("java.io.tmpdir"));
        } else {
            backendTempDir = new File(homeDir + "/repository/datastore/tmp");
            backendTempDir.mkdirs();
        }

        log.info("Creating DataRecordWriter with direct Backend access and temp dir at '{}'.",
                backendTempDir.getAbsolutePath());
        return new DataRecordWriter(backend, backendTempDir);
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new MigrationJobExecutionListener();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(getMinWorkers());
        taskExecutor.setMaxPoolSize(getMaxWorkers());
        taskExecutor.setDaemon(true);
        return taskExecutor;
    }

    @Bean
    public Job migrationJob() {
        return jobBuilderFactory.get("migrationJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        try {
            return stepBuilderFactory.get("step1")
                    .<DataRecord, DataRecord> chunk(10)
                    .reader(dataRecordReader())
                    .writer(dataRecordWriter())
                    .taskExecutor(taskExecutor())
                    .build();
        } catch (RepositoryException e) {
            throw new StepBuilderException(e);
        }
    }
}