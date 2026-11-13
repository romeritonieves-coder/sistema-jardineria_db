package com.jardineria.application.ports.out;

import com.jardineria.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    void guardar(Cliente cliente);
    List<Cliente> listarTodos();
    Optional<Cliente> buscarPorId(int id);
    void actualizar(Cliente cliente);
}
