package com.jardineria.infrastructure.adapters.out.persistence;

import com.jardineria.application.ports.out.ProductoRepository;
import com.jardineria.domain.model.Producto;
import com.jardineria.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoRepositoryJdbc implements ProductoRepository {
    private final Connection connection;

    public ProductoRepositoryJdbc() {
        this.connection = DatabaseConfig.getInstancia().getConnection();
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(Producto.builder()
                        .codigoProducto(rs.getString("codigo_producto"))
                        .nombre(rs.getString("nombre"))
                        .gama(rs.getString("gama"))
                        .cantidadEnStock(rs.getInt("cantidad_en_stock"))
                        .precioVenta(rs.getBigDecimal("precio_venta"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public void guardar(Producto p) {
        String sql = "INSERT INTO producto (codigo_producto, nombre, gama, cantidad_en_stock, precio_venta) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, p.getCodigoProducto());
            pstmt.setString(2, p.getNombre());
            pstmt.setString(3, p.getGama());
            pstmt.setInt(4, p.getCantidadEnStock());
            pstmt.setBigDecimal(5, p.getPrecioVenta());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Producto> buscarPorId(String id) {
        return Optional.empty();
    }

    @Override
    public void actualizar(Producto producto) {
    }
}
