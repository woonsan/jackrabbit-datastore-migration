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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DataStoreConfiguration {

    private String homeDir;

    private String className;

    private Properties params;

    public String getHomeDir() {
        return homeDir;
    }

    public void setHomeDir(String homeDir) {
        this.homeDir = homeDir;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Properties getParams() {
        return params;
    }

    public void setParams(Properties params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof DataStoreConfiguration)) {
            return false;
        }

        DataStoreConfiguration that = (DataStoreConfiguration) o;

        return new EqualsBuilder()
                .append(this.homeDir, that.homeDir)
                .append(this.className, that.className)
                .append(this.params, that.params)
                .build()
                .booleanValue();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(homeDir)
                .append(className)
                .append(params)
                .build()
                .intValue();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("homeDir", homeDir)
                .append("className", className)
                .append("params", params)
                .build();
    }
}
