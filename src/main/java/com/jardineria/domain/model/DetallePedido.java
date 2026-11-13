package com.jardineria.domain.model;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class DetallePedido {
    private int codigoPedido;
    private String codigoProducto;
    private int cantidad;
    private BigDecimal precioUnidad;
    private short numeroLinea;
}
