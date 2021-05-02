package com.sdk.database.repository;

import java.util.Map;

public interface SqlListener {

    String getSelectSQL(String table, String [] columns, String condition, String orderby);

    String getInsertSQL(String table, String [] values);
    
    String getInsertSQL(String table, Map<String, String> colValues);

    String getDeleteSQL(String table, String condition);

    String getUpdateSQL(String table, Map<String, String> colValues, String condition);
}
