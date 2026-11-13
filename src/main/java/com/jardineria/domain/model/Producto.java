package com.jardineria.domain.model;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class Producto {
    private String codigoProducto;
    private String nombre;
    private String gama;
    private String dimensiones;
    private String proveedor;
    private String descripcion;
    private int cantidadEnStock;
    private BigDecimal precioVenta;
    private BigDecimal precioProveedor;
}
