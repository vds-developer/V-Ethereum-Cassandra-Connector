package com.vconnector.cassandra.models;

import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;

public interface IBaseTableActions {
    public CreateTable createTableStatement();

}
