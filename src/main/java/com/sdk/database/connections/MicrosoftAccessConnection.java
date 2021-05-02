package com.sdk.database.connections;

import com.sdk.storage.base.FileOperation;
import java.sql.Connection;
import java.sql.DriverManager;

public class MicrosoftAccessConnection {

    private Connection connection;
    private String connectionString;

    private String path;
    private String databaseName;

    public MicrosoftAccessConnection(String path) {
        this.path = path;
        connect();
    }

    public boolean connect() {
        try {
            FileOperation fp = new FileOperation(path);

            if (fp.exists()) {
                connectionString = "jdbc:ucanaccess://".concat(path);
                databaseName = fp.getName();
                
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                connection = DriverManager.getConnection(this.path);
                
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

    public String getDatabaseName() {
        return databaseName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
