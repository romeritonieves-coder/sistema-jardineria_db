package com.jardineria.infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static DatabaseConfig instancia;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/jardineria";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private DatabaseConfig() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }

    public static synchronized DatabaseConfig getInstancia() {
        if (instancia == null) {
            instancia = new DatabaseConfig();
        }
        return instancia;
    }

    public Connection getConnection() {
        return connection;
    }
}
