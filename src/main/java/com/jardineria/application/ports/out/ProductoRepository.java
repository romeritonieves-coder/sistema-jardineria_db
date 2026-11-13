package com.jardineria.application.ports.out;

import com.jardineria.domain.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    void guardar(Producto producto);
    List<Producto> listarTodos();
    Optional<Producto> buscarPorId(String id);
    void actualizar(Producto producto);
}
