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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.jackrabbit.core.data.DataRecord;
import org.apache.jackrabbit.core.data.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

public class DataRecordWriter implements ItemWriter<DataRecord> {

    private static Logger log = LoggerFactory.getLogger(DataRecordWriter.class);

    private DataStore dataStore;

    public DataRecordWriter(final DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void write(List<? extends DataRecord> items) throws Exception {
        DataRecord addedRecord;

        for (DataRecord record : items) {
            InputStream input = null;

            try {
                input = record.getStream();
                addedRecord = dataStore.addRecord(input);
                log.debug("Added record ({}) to a new record ({}).", record.getIdentifier(),
                        addedRecord.getIdentifier());
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        log.error("Error while closing input stream.", e);
                    }
                }
            }
        }
    }

}
