package com.jardineria.infrastructure.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseSeeder {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:jardineria.db";
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            
            String sqlContent = new String(Files.readAllBytes(Paths.get("init_sqlite.sql")));
            String[] queries = sqlContent.split(";");
            
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query);
                }
            }
            System.out.println("Base de dades SQLite inicialitzada correctament.");
        } catch (Exception e) {
            System.err.println("Error en inicialitzar la base de dades: " + e.getMessage());
        }
    }
}
