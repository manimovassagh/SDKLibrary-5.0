package com.sdk.database.connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlServerConnection {

    private Connection connection;
    private String connectionString;

    private int port;

    private String host;
    private String instance;
    private String username;
    private String password;
    private String databaseName;

    public SqlServerConnection(String host, String instance, String databaseName, String username, String password, int port) {
        this.host = host;
        this.instance = instance;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.port = port;

        connect();
    }

    public boolean connect() {
        try {
            connectionString = "jdbc:sqlserver://" + host + "\\" + instance + ":" + port + ";database=" + databaseName;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

            this.connection = DriverManager.getConnection(connectionString, username, password);
            
            return true;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    
    

}
