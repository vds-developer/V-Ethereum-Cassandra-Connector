package com.vconnector.cassandra.models;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTableStart;
import com.datastax.oss.protocol.internal.ProtocolConstants;

import java.util.ArrayList;
import java.util.List;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;

public class BlockTable extends BaseTable{
    public BlockTable (String name) {
        super(name);
        partitionColumns.add(new ColumnDefinition(CqlIdentifier.fromInternal("number") , DataTypes.BIGINT));
        clusterKeys.add(new ColumnDefinition(CqlIdentifier.fromInternal("timestamp"), DataTypes.TIMESTAMP));

        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("basefeepergas"), DataTypes.BIGINT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("difficulty"), DataTypes.BIGINT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("extradata"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("gaslimit"), DataTypes.BIGINT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("gasused"), DataTypes.BIGINT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("hash"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("logsbloom"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("miner"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("mixhash"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("nonce"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("parenthash"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("receiptsroot"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("sha3uncles"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("size"), DataTypes.BIGINT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("stateroot"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("totaldifficulty"), DataTypes.BIGINT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("transactionsroot"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("transactions"), DataTypes.listOf(DataTypes.TEXT)));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("transactionsroot"), DataTypes.TEXT));
        column.add(new ColumnDefinition(CqlIdentifier.fromInternal("uncles"), DataTypes.listOf(DataTypes.TEXT)));


    }
    @Override
    public CreateTable createTableStatement() {
        CreateTable statement = super.createTableStatement();
        statement.withClusteringOrder(CqlIdentifier.fromInternal("timestamp"), ClusteringOrder.DESC);
        return statement;
    }

    public static void main (String[] args) {
        BlockTable blockTable = new BlockTable("test");
        blockTable.createTableStatement();
    }

}
