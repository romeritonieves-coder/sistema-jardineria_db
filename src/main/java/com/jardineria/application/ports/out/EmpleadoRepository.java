package com.jardineria.application.ports.out;

import com.jardineria.domain.model.Empleado;
import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository {
    void guardar(Empleado empleado);
    List<Empleado> listarTodos();
    Optional<Empleado> buscarPorId(int id);
    void actualizar(Empleado empleado);
}
