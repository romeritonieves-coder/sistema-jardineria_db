package com.jardineria.application.services;

import com.jardineria.application.ports.out.EmpleadoRepository;
import com.jardineria.domain.model.Empleado;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodos() {
        return empleadoRepository.listarTodos();
    }
}
