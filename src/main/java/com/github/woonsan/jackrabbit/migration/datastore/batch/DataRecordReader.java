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

import org.apache.jackrabbit.core.data.DataIdentifier;
import org.apache.jackrabbit.core.data.DataRecord;
import org.apache.jackrabbit.core.data.DataStore;
import org.apache.jackrabbit.core.data.DataStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

public class DataRecordReader implements ItemReader<DataRecord> {

    private static Logger log = LoggerFactory.getLogger(DataRecordReader.class);

    @Autowired
    private MigrationJobExecutionStates executionStates;

    private DataStore dataStore;

    public DataRecordReader(final DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public DataRecord read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        final DataIdentifier id = executionStates.getNextSourceDataIdentifier();

        DataRecord record = null;

        if (id != null) {
            try {
                record = dataStore.getRecord(id);
                executionStates.reportReadSuccess(id);
            } catch (DataStoreException e) {
                executionStates.reportReadError(id, e.toString());
            }
        }

        return record;
    }
}
