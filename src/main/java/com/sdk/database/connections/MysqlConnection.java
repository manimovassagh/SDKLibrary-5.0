package com.sdk.database.connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConnection {

    private Connection connection;
    private String connectionString;
    
    private int port;

    private String ip;
    private String username;
    private String password;
    private String databaseName;

    public MysqlConnection(String ip, int port, String username, String password, String databaseName) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;

        connect();
    }

    public MysqlConnection(String databaseName) {
        this.ip = "localhost";
        this.port = 3306;
        this.username = "root";
        this.password = "";
        this.databaseName = databaseName;

        connect();
    }

    public boolean connect() {
        try {
            connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + databaseName;
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection(connectionString, username, password);
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
