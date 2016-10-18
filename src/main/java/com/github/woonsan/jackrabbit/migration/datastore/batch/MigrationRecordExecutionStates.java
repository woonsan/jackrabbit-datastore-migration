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

public class MigrationRecordExecutionStates {

    private final DataIdentifier identifier;
    private long writeSize;
    private boolean readSucceeded;
    private String readError;
    private boolean writeSucceeded;
    private String writeError;

    public MigrationRecordExecutionStates(final DataIdentifier identifier) {
        this.identifier = identifier;
    }

    public DataIdentifier getIdentifier() {
        return identifier;
    }

    public boolean isReadSucceeded() {
        return readSucceeded;
    }

    public void setReadSucceeded(boolean readSucceeded) {
        this.readSucceeded = readSucceeded;
    }

    public long getWriteSize() {
        return writeSize;
    }

    public void setWriteSize(long writeSize) {
        this.writeSize = writeSize;
    }

    public String getReadError() {
        return readError;
    }

    public void setReadError(String readError) {
        this.readError = readError;
    }

    public boolean isWriteSucceeded() {
        return writeSucceeded;
    }

    public void setWriteSucceeded(boolean writeSucceeded) {
        this.writeSucceeded = writeSucceeded;
    }

    public String getWriteError() {
        return writeError;
    }

    public void setWriteError(String writeError) {
        this.writeError = writeError;
    }

}
