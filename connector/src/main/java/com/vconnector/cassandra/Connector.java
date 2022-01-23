package com.vconnector.cassandra;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.datastax.oss.driver.api.mapper.annotations.Select;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.insert.JsonInsert;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.insertInto;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.*;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspaceStart;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTableStart;
import com.datastax.oss.driver.api.querybuilder.select.SelectFrom;
import com.vconnector.cassandra.models.BlockTable;

import javax.swing.plaf.nimbus.State;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Connector {
    private CqlSession cqlSession;
    public Connector(String node, int port, String keySpace) {
        cqlSession = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(node, port))
                .withLocalDatacenter("datacenter1")
                .withKeyspace(keySpace)
                .build();
    }

    public void test() {
        ResultSet rs = cqlSession.execute("select release_version from system.local");              // (2)
        Row row = rs.one();
        System.out.println(row.getString("release_version"));

    }

    public void createKeySpaceAndExecute(String namespace) {
        CreateKeyspace createNamespaceQuery = createKeyspace(namespace).ifNotExists().withSimpleStrategy(3);
        ResultSet rs = cqlSession.execute(createNamespaceQuery.build());
    }

    public void createTableAndExecute(String tableName) {
        BlockTable tb = new BlockTable(tableName);
        CreateTable statement = tb.createTableStatement();
        ResultSet result = cqlSession.execute(statement.build());
    }

    public void appendJson(String table, String json) {
        JsonInsert statement = insertInto(table).json(json);
        cqlSession.execute(statement.build());
    }

    public void getKeySpace(){
        ResultSet rs = cqlSession.execute("Describe keyspaces");
        System.out.println(rs.all());
    }
}