package com.sdk.database.repository;

import java.util.Map;

public interface TableListener {
    boolean searchTable(String table, String column, String key);
    
    boolean removeTable(String table);
    
    boolean createTable(String table, Map<String, String> columns, String id);
    
    boolean tableExists(String table);
    
    long countRecords(String table);
}
