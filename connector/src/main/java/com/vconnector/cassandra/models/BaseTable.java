package com.vconnector.cassandra.models;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTableStart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;

public abstract class BaseTable implements IBaseTableActions {
    List<ColumnDefinition> partitionColumns;
    List<ColumnDefinition> clusterKeys;
    List<ColumnDefinition> column;
    String name;

    // for now use default keyspace
    public BaseTable(String name) {
        this.partitionColumns = new ArrayList<>();
        this.clusterKeys = new ArrayList<>();
        this.column = new ArrayList<>();
        this.name = name;
    }

    public CreateTable createTableStatement() {
        CreateTable c = null; CreateTableStart cs;
//        cs = createTable(CqlIdentifier.fromInternal(this.name)).ifNotExists();
        cs = createTable(this.name).ifNotExists();
        for (ColumnDefinition columnDefinition : partitionColumns) {
            c = cs.withPartitionKey(columnDefinition.name, columnDefinition.type);
        }
        for (ColumnDefinition columnDefinition: clusterKeys){
            c = c.withClusteringColumn(columnDefinition.name, columnDefinition.type);
        }
        for (ColumnDefinition columnDefinition: column) {
            c = c.withColumn(columnDefinition.name, columnDefinition.type);
        }
        return c;
    }



    class ColumnDefinition {
        DataType type;
        CqlIdentifier name;
        ColumnDefinition(CqlIdentifier name, DataType type) {
            this.type = type;
            this.name = name;
        }
    }

}
