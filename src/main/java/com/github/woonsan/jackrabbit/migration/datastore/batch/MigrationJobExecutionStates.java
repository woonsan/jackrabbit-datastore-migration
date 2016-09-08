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

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.core.data.DataIdentifier;
import org.apache.jackrabbit.core.data.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MigrationJobExecutionStates {

    private static Logger log = LoggerFactory.getLogger(MigrationJobExecutionStates.class);

    @Autowired
    private DataStoreFactory dataStoreFactory;

    @Autowired
    private SourceDataStoreConfiguration sourceDataStoreConfiguration;

    private long startedTimeMillis;

    private long stoppedTimeMillis;

    private volatile int recordCount;

    private volatile Map<DataIdentifier, MigrationRecordExecutionStates> executionStatesMap;

    private volatile Iterator<DataIdentifier> executionStatesDataIdentifiersIterator;

    public long getStartedTimeMillis() {
        return startedTimeMillis;
    }

    public void setStartedTimeMillis(long startedTimeMillis) {
        this.startedTimeMillis = startedTimeMillis;
    }

    public long getStoppedTimeMillis() {
        return stoppedTimeMillis;
    }

    public void setStoppedTimeMillis(long stoppedTimeMillis) {
        this.stoppedTimeMillis = stoppedTimeMillis;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public synchronized DataIdentifier getNextSourceDataIdentifier() {
        Iterator<DataIdentifier> idsIt = getSourceDataIdentifiers();

        if (idsIt == null || !idsIt.hasNext()) {
            return null;
        }

        return idsIt.next();
    }

    public Map<DataIdentifier, MigrationRecordExecutionStates> getExecutionStatesMap() {
        if (executionStatesMap == null) {
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(executionStatesMap);
    }

    public void reportReadSuccess(final DataIdentifier identifier) {
        final MigrationRecordExecutionStates recordStates = executionStatesMap.get(identifier);
        recordStates.setReadSucceeded(true);
    }

    public void reportReadError(final DataIdentifier identifier, final String errorMessage) {
        final MigrationRecordExecutionStates recordStates = executionStatesMap.get(identifier);
        recordStates.setReadSucceeded(false);
        recordStates.setReadError(errorMessage);
    }

    public void reportWriteSuccess(final DataIdentifier identifier) {
        final MigrationRecordExecutionStates recordStates = executionStatesMap.get(identifier);
        recordStates.setWriteSucceeded(true);
    }

    public void reportWriteError(final DataIdentifier identifier, final String errorMessage) {
        final MigrationRecordExecutionStates recordStates = executionStatesMap.get(identifier);
        recordStates.setWriteSucceeded(false);
        recordStates.setWriteError(errorMessage);
    }

    public void reset() {
        executionStatesDataIdentifiersIterator = null;
        executionStatesMap = null;
    }

    private Iterator<DataIdentifier> getSourceDataIdentifiers() {
        Iterator<DataIdentifier> idsIt = executionStatesDataIdentifiersIterator;

        if (idsIt == null) {
            synchronized (this) {
                idsIt = executionStatesDataIdentifiersIterator;

                if (idsIt == null) {
                    try {
                        recordCount = 0;
                        executionStatesMap = new LinkedHashMap<>();
                        final DataStore sourceDataStore = dataStoreFactory.getDataStore(sourceDataStoreConfiguration);
                        Iterator<DataIdentifier> allIds = sourceDataStore.getAllIdentifiers();

                        DataIdentifier id;
                        MigrationRecordExecutionStates recordState;

                        while (allIds.hasNext()) {
                            id = allIds.next();
                            ++recordCount;
                            recordState = new MigrationRecordExecutionStates(id);
                            executionStatesMap.put(id, recordState);
                        }
                    } catch (RepositoryException e) {
                        log.error("Error while retrieving all data identifiers from source dataStore.", e);
                    }

                    executionStatesDataIdentifiersIterator = idsIt = executionStatesMap.keySet().iterator();
                }
            }
        }

        return idsIt;
    }
}
