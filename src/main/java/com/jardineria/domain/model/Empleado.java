package com.jardineria.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Empleado {
    private int codigoEmpleado;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String extension;
    private String email;
    private String codigoOficina;
    private Integer codigoJefe;
    private String puesto;
}
