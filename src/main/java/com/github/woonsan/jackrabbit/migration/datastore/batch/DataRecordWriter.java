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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.core.data.Backend;
import org.apache.jackrabbit.core.data.DataIdentifier;
import org.apache.jackrabbit.core.data.DataRecord;
import org.apache.jackrabbit.core.data.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class DataRecordWriter implements ItemWriter<DataRecord> {

    private static Logger log = LoggerFactory.getLogger(DataRecordWriter.class);

    @Autowired
    private MigrationJobExecutionStates executionStates;

    private final DataStore dataStore;

    private final Backend backend;

    private final File backendTempDir;

    public DataRecordWriter(final DataStore dataStore) {
        this.dataStore = dataStore;
        this.backend = null;
        this.backendTempDir = null;
    }

    public DataRecordWriter(final Backend backend, final File backendTempDir) {
        this.dataStore = null;
        this.backend = backend;
        this.backendTempDir = backendTempDir;
    }

    @Override
    public void write(List<? extends DataRecord> items) throws Exception {
        for (DataRecord record : items) {
            final DataIdentifier identifier = record.getIdentifier();

            try {
                final long recordLength;

                if (backend == null) {
                    recordLength = addRecordThroughDataStore(record);
                } else {
                    recordLength = addRecordThroughBackend(identifier, record);
                }

                executionStates.reportWriteSize(identifier, recordLength);
                executionStates.reportWriteSuccess(identifier);
                log.info("Record migrated: '{}' ({}%ile)", identifier,
                        String.format("%2.1f", 100.0 * executionStates.getWriteProgress()));
            } catch (Exception e) {
                executionStates.reportWriteError(identifier, e.toString());
            }
        }
    }

    private long addRecordThroughDataStore(final DataRecord record) throws Exception {
        InputStream input = null;

        try {
            input = record.getStream();
            final DataRecord addedRecord = dataStore.addRecord(input);
            return addedRecord.getLength();
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    private long addRecordThroughBackend(final DataIdentifier identifier, final DataRecord record)
            throws Exception {
        InputStream input = null;
        OutputStream output = null;
        File tempFile = null;

        try {
            input = record.getStream();
            tempFile = File.createTempFile("tmp", null, backendTempDir);
            output = new FileOutputStream(tempFile);

            IOUtils.copyLarge(input, output);
            IOUtils.closeQuietly(output);
            output = null;

            backend.write(identifier, tempFile);
            return record.getLength();
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(input);
            FileUtils.deleteQuietly(tempFile);
        }
    }
}
