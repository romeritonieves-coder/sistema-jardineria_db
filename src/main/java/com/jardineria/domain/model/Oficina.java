package com.jardineria.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Oficina {
    private String codigoOficina;
    private String ciudad;
    private String pais;
    private String region;
    private String codigoPostal;
    private String telefono;
}
