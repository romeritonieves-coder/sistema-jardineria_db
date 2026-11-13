package com.jardineria.infrastructure.adapters.out.persistence;

import com.jardineria.application.ports.out.EmpleadoRepository;
import com.jardineria.domain.model.Empleado;
import com.jardineria.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpleadoRepositoryJdbc implements EmpleadoRepository {
    private final Connection connection;

    public EmpleadoRepositoryJdbc() {
        this.connection = DatabaseConfig.getInstancia().getConnection();
    }

    @Override
    public List<Empleado> listarTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                empleados.add(Empleado.builder()
                        .codigoEmpleado(rs.getInt("codigo_empleado"))
                        .nombre(rs.getString("nombre"))
                        .email(rs.getString("email"))
                        .puesto(rs.getString("puesto"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public void guardar(Empleado empleado) {
        String sql = "INSERT INTO empleado (codigo_empleado, nombre, email, puesto) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, empleado.getCodigoEmpleado());
            pstmt.setString(2, empleado.getNombre());
            pstmt.setString(3, empleado.getEmail());
            pstmt.setString(4, empleado.getPuesto());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Empleado> buscarPorId(int id) {
        return Optional.empty();
    }

    @Override
    public void actualizar(Empleado empleado) {
    }
}
