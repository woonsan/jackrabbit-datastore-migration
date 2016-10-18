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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.core.data.DataIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MigrationJobExecutionReporter {

    private static Logger log = LoggerFactory.getLogger(MigrationJobExecutionReporter.class);

    @Autowired
    private MigrationJobExecutionStates executionStates;

    public void report() {
        StringWriter sw = new StringWriter(4096);
        PrintWriter out = new PrintWriter(sw);
        out.println();
        printJobExecutionSummary(out);
        out.flush();
        log.info(sw.toString());
    }

    public void printJobExecutionSummary(PrintWriter out) {
        int totalCount = executionStates.getRecordCount();
        int processedRecordCount = 0;
        int readSuccessCount = 0;
        int writeSuccessCount = 0;

        DataIdentifier identifier;
        MigrationRecordExecutionStates recordStates;

        for (Map.Entry<DataIdentifier, MigrationRecordExecutionStates> entry : executionStates.getExecutionStatesMap().entrySet()) {
            ++processedRecordCount;
            identifier = entry.getKey();
            recordStates = entry.getValue();

            if (recordStates.isReadSucceeded()) {
                ++readSuccessCount;
            }

            if (recordStates.isWriteSucceeded()) {
                ++writeSuccessCount;
            }
        }

        out.println(
                "===============================================================================================================");
        out.println("Execution Summary:");
        out.println(
                "---------------------------------------------------------------------------------------------------------------");
        out.printf("Total: %d, Processed: %d, Read Success: %d, Read Fail: %d, Write Success: %d, Write Fail: %d, Duration: %dms", 
                totalCount, processedRecordCount,
                readSuccessCount, (processedRecordCount - readSuccessCount),
                writeSuccessCount, (processedRecordCount - writeSuccessCount),
                executionStates.getStoppedTimeMillis() - executionStates.getStartedTimeMillis());
        out.println();
        out.println(
                "---------------------------------------------------------------------------------------------------------------");
        out.println("Details (in CSV format):");
        out.println(
                "---------------------------------------------------------------------------------------------------------------");

        CSVPrinter csvPrinter = null;

        try {
            csvPrinter = CSVFormat.DEFAULT
                    .withHeader("SEQ", "ID", "READ", "WRITE", "SIZE", "ERROR")
                    .print(out);

            int seq = 0;

            for (Map.Entry<DataIdentifier, MigrationRecordExecutionStates> entry : executionStates.getExecutionStatesMap().entrySet()) {
                identifier = entry.getKey();
                recordStates = entry.getValue();

                csvPrinter.printRecord(++seq,
                        identifier,
                        recordStates.isReadSucceeded(),
                        recordStates.isWriteSucceeded(),
                        recordStates.getWriteSize(),
                        StringUtils.isNotEmpty(recordStates.getReadError()) ? recordStates.getReadError() : recordStates.getWriteError());
            }
        } catch (IOException e) {
            log.error("Error while printing result in CSV.");
        }

        out.println(
                "===============================================================================================================");
    }

}
