package com.jardineria.application.services;

import com.jardineria.application.ports.out.ClienteRepository;
import com.jardineria.domain.model.Cliente;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        return clienteRepository.listarTodos();
    }
}
