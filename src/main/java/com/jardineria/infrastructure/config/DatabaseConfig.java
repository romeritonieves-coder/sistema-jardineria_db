package com.jardineria.infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static DatabaseConfig instancia;
    private Connection connection;

    private static final String URL = "jdbc:sqlite:jardineria.db";

    private DatabaseConfig() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con SQLite", e);
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
