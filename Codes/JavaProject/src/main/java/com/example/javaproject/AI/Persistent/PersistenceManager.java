package com.example.javaproject.AI.Persistent;

import java.sql.Connection;
import java.sql.DriverManager;

public class PersistenceManager {
    private static final Connection connection = createConnection();

    public PersistenceManager() {

    }

    public static Connection getConnection() {
        return connection;
    }

    private static Connection createConnection() {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection("jdbc:h2:data/data", "sa", "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}