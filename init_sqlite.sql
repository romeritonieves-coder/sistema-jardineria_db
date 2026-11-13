DROP TABLE IF EXISTS detalle_pedido;
DROP TABLE IF EXISTS pedido;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS gama_producto;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS empleado;
DROP TABLE IF EXISTS oficina;

CREATE TABLE oficina (
    codigo_oficina VARCHAR(10) PRIMARY KEY,
    ciudad VARCHAR(30) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    region VARCHAR(50),
    codigo_postal VARCHAR(10) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

CREATE TABLE empleado (
    codigo_empleado INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido1 VARCHAR(50) NOT NULL,
    apellido2 VARCHAR(50),
    extension VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    codigo_oficina VARCHAR(10) NOT NULL,
    codigo_jefe INT,
    puesto VARCHAR(50),
    FOREIGN KEY (codigo_oficina) REFERENCES oficina(codigo_oficina),
    FOREIGN KEY (codigo_jefe) REFERENCES empleado(codigo_empleado)
);

CREATE TABLE cliente (
    codigo_cliente INT PRIMARY KEY,
    nombre_cliente VARCHAR(50) NOT NULL,
    nombre_contacto VARCHAR(30),
    apellido_contacto VARCHAR(30),
    telefono VARCHAR(20) NOT NULL,
    fax VARCHAR(20) NOT NULL,
    linea_direccion1 VARCHAR(50) NOT NULL,
    linea_direccion2 VARCHAR(50),
    ciudad VARCHAR(50) NOT NULL,
    region VARCHAR(50),
    pais VARCHAR(50),
    codigo_postal VARCHAR(10),
    codigo_empleado_rep_ventas INT,
    limite_credito DECIMAL(15,2),
    FOREIGN KEY (codigo_empleado_rep_ventas) REFERENCES empleado(codigo_empleado)
);

CREATE TABLE gama_producto (
    gama VARCHAR(50) PRIMARY KEY,
    descripcion_text TEXT,
    descripcion_html TEXT,
    imagen VARCHAR(256)
);

CREATE TABLE producto (
    codigo_producto VARCHAR(15) PRIMARY KEY,
    nombre VARCHAR(70) NOT NULL,
    gama VARCHAR(50) NOT NULL,
    dimensiones VARCHAR(25),
    proveedor VARCHAR(50),
    descripcion text,
    cantidad_en_stock SMALLINT NOT NULL,
    precio_venta DECIMAL(15,2) NOT NULL,
    precio_proveedor DECIMAL(15,2),
    FOREIGN KEY (gama) REFERENCES gama_producto(gama)
);

-- Insertar Datos Ficticios / Semilla de Alta Calidad
INSERT INTO oficina VALUES ('BCN-ES', 'Bucaramanga', 'Colombia', 'Santander', '680001', '+57 607 6300000');
INSERT INTO oficina VALUES ('BOG-ES', 'Bogotá', 'Colombia', 'Cundinamarca', '110111', '+57 601 7400000');

INSERT INTO empleado VALUES (1, 'Carlos', 'Pérez', 'Gómez', '1234', 'carlos.perez@jardineria.com', 'BCN-ES', NULL, 'Director General');
INSERT INTO empleado VALUES (2, 'Ana', 'Rodríguez', 'López', '1235', 'ana.rodriguez@jardineria.com', 'BCN-ES', 1, 'Subdirector Comercial');
INSERT INTO empleado VALUES (3, 'Jhon', 'Romero', 'Nieves', '1236', 'jhon.romero@jardineria.com', 'BCN-ES', 2, 'Senior Systems Developer');
INSERT INTO empleado VALUES (4, 'Luisa', 'Martínez', 'Sánchez', '1237', 'luisa.martinez@jardineria.com', 'BOG-ES', 1, 'Gerente de Ventas');

INSERT INTO gama_producto VALUES ('Frutales', 'Árboles y arbustos frutales para jardín', NULL, NULL);
INSERT INTO gama_producto VALUES ('Ornamentales', 'Plantas y flores decorativas de exterior e interior', NULL, NULL);
INSERT INTO gama_producto VALUES ('Herramientas', 'Herramientas manuales y eléctricas de jardinería', NULL, NULL);
INSERT INTO gama_producto VALUES ('Riego', 'Sistemas avanzados de riego por goteo y aspersión', NULL, NULL);

INSERT INTO producto VALUES ('PROD-001', 'Rosal Trepador Rojo', 'Ornamentales', '1.2m', 'Viveros Andinos', 'Rosal de alta resistencia', 450, 35000.00, 18000.00);
INSERT INTO producto VALUES ('PROD-002', 'Manzano Gala Enano', 'Frutales', '1.5m', 'Frutas del Norte', 'Produce frutos dulces en espacios pequeños', 120, 85000.00, 42000.00);
INSERT INTO producto VALUES ('PROD-003', 'Kit de Riego por Goteo 50m', 'Riego', 'N/A', 'HydroTech', 'Sistema automatizado con programador', 85, 145000.00, 75000.00);
INSERT INTO producto VALUES ('PROD-004', 'Tijeras de Podar Profesional', 'Herramientas', '20cm', 'Gardena Tools', 'Hoja de acero templado antiadherente', 320, 52000.00, 25000.00);
INSERT INTO producto VALUES ('PROD-005', 'Palmera Abanico', 'Ornamentales', '2.0m', 'Viveros Andinos', 'Palmera ideal para exteriores soleados', 60, 210000.00, 110000.00);
INSERT INTO producto VALUES ('PROD-006', 'Cerezo Sweet Early', 'Frutales', '1.8m', 'Frutas del Norte', 'Árbol frutal de rápida maduración', 95, 95000.00, 48000.00);
INSERT INTO producto VALUES ('PROD-007', 'Manguera Retráctil 25m', 'Riego', 'N/A', 'HydroTech', 'Manguera reforzada con soporte de pared', 210, 115000.00, 60000.00);

INSERT INTO cliente VALUES (101, 'AgroEmpresas S.A.S.', 'Mauricio', 'Silva', '+573104567890', 'N/A', 'Calle 50 # 25-10', 'Oficina 302', 'Bucaramanga', 'Santander', 'Colombia', '680002', 2, 15000000.00);
INSERT INTO cliente VALUES (102, 'Vivero El Paraíso', 'Carmen', 'Díaz', '+573157891234', 'N/A', 'Carrera 27 # 45-12', NULL, 'Bucaramanga', 'Santander', 'Colombia', '680003', 3, 8500000.00);
INSERT INTO cliente VALUES (103, 'Constructora Urbania', 'Esteban', 'Rojas', '+573201234567', 'N/A', 'Av. El Dorado # 68-90', 'Torre B', 'Bogotá', 'Cundinamarca', 'Colombia', '110221', 4, 30000000.00);
INSERT INTO cliente VALUES (104, 'Jardines del Este Ltda', 'Sofia', 'Navarro', '+573119876543', 'N/A', 'Calle 85 # 15-40', NULL, 'Bogotá', 'Cundinamarca', 'Colombia', '110222', 4, 12000000.00);
INSERT INTO cliente VALUES (105, 'Finca El Descanso', 'Rodrigo', 'Mora', '+573005554433', 'N/A', 'Vía Ruitoque Km 4', 'Hacienda', 'Floridablanca', 'Santander', 'Colombia', '681001', 2, 5000000.00);
