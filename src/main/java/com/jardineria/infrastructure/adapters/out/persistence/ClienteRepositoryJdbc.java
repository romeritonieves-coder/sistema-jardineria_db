package com.jardineria.infrastructure.adapters.out.persistence;

import com.jardineria.application.ports.out.ClienteRepository;
import com.jardineria.domain.model.Cliente;
import com.jardineria.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepositoryJdbc implements ClienteRepository {
    private final Connection connection;

    public ClienteRepositoryJdbc() {
        this.connection = DatabaseConfig.getInstancia().getConnection();
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(Cliente.builder()
                        .codigoCliente(rs.getInt("codigo_cliente"))
                        .nombreCliente(rs.getString("nombre_cliente"))
                        .telefono(rs.getString("telefono"))
                        .limiteCredito(rs.getBigDecimal("limite_credito"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public void guardar(Cliente cliente) {
        String sql = "INSERT INTO cliente (codigo_cliente, nombre_cliente, telefono, limite_credito) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cliente.getCodigoCliente());
            pstmt.setString(2, cliente.getNombreCliente());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setBigDecimal(4, cliente.getLimiteCredito());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Cliente> buscarPorId(int id) {
        return Optional.empty();
    }

    @Override
    public void actualizar(Cliente cliente) {
    }
}
