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

import java.util.Properties;

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.core.config.BeanConfig;
import org.apache.jackrabbit.core.config.BeanFactory;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.SimpleBeanFactory;
import org.apache.jackrabbit.core.data.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataStoreFactory {

    private static Logger log = LoggerFactory.getLogger(DataStoreFactory.class);

    private BeanFactory beanFactory = new SimpleBeanFactory();

    public DataStore get(final DataStoreConfiguration dataStoreConfiguration) {
        log.debug("dataStoreConfiguration: {}", dataStoreConfiguration);

        DataStore dataStore = null;

        try {
            String homeDir = dataStoreConfiguration.getHomeDir();
            String className = dataStoreConfiguration.getClassName();
            Class<?> clazz = Class.forName(className);
            Properties params = dataStoreConfiguration.getParams();
            BeanConfig beanConfig = new BeanConfig(className, params);
            dataStore = (DataStore) beanFactory.newInstance(clazz, beanConfig);
            log.debug("dataStore: {}", dataStore);
            dataStore.init(homeDir);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.toString());
        } catch (ConfigurationException e) {
            throw new RuntimeException(e.toString());
        } catch (RepositoryException e) {
            throw new RuntimeException(e.toString());
        }

        return dataStore;
    }

}
