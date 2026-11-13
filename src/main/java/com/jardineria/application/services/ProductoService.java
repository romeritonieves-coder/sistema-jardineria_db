package com.jardineria.application.services;

import com.jardineria.application.ports.out.ProductoRepository;
import com.jardineria.domain.model.Producto;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.listarTodos();
    }
    
    public void registrarNuevoProducto(Producto producto) {
        if (producto.getPrecioVenta().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El preu de venda ha de ser positiu");
        }
        productoRepository.guardar(producto);
    }
}
