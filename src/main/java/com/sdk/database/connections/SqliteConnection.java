package com.sdk.database.connections;

import com.sdk.storage.base.FileOperation;
import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteConnection {

    private Connection connection;
    private String connectionString;
    
    private String path;
    private String databaseName;

    public SqliteConnection(String path) {
        this.path = path;
        connect();
    }

    public boolean connect() {
        try {
            FileOperation fp = new FileOperation(path);
            
            if (fp.exists()) {
                Class.forName("org.sqlite.JDBC");
                
                connectionString = "jdbc:sqlite:".concat(path);
                databaseName = fp.getName();
                
                connection = DriverManager.getConnection(connectionString);
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
