package com.github.woonsan.jackrabbit.migration.datastore.batch;

import org.apache.jackrabbit.core.data.DataRecord;
import org.apache.jackrabbit.core.data.DataStore;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class DataRecordReader implements ItemReader<DataRecord> {

    private DataStore dataStore;

    public DataRecordReader(final DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public DataRecord read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }

}
