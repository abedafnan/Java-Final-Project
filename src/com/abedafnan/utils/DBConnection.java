package com.abedafnan.utils;

import java.sql.*;

public class DBConnection {
    private Connection connection;
    private Statement statement;
    private static  DBConnection dbConnection;

    private DBConnection() {}

    public static DBConnection getDbConnection() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Statement createStatement() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/customerorder";
            connection = DriverManager.getConnection(url, "root", "root");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public void closeConnection() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
