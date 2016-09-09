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

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.core.data.DataRecord;
import org.apache.jackrabbit.core.data.DataStore;
import org.apache.jackrabbit.core.data.DataStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class DataRecordWriter implements ItemWriter<DataRecord> {

    private static Logger log = LoggerFactory.getLogger(DataRecordWriter.class);

    @Autowired
    private MigrationJobExecutionStates executionStates;

    private DataStore dataStore;

    public DataRecordWriter(final DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void write(List<? extends DataRecord> items) throws Exception {
        for (DataRecord record : items) {
            InputStream input = null;

            try {
                input = record.getStream();
                dataStore.addRecord(input);
                executionStates.reportWriteSuccess(record.getIdentifier());
                log.info("Record migrated: '{}' ({}%ile)", record.getIdentifier(),
                        String.format("%2.1f", 100.0 * executionStates.getWriteProgress()));
            } catch (DataStoreException e) {
                executionStates.reportWriteError(record.getIdentifier(), e.toString());
            } finally {
                IOUtils.closeQuietly(input);
            }
        }
    }

}
