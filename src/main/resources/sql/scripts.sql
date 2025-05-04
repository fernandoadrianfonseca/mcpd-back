DROP TABLE productos;
DROP TABLE productos_stock;
DROP TABLE productos_categorias;

CREATE TABLE productos_categorias (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(MAX) NOT NULL,
);

CREATE TABLE productos (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(MAX) NOT NULL,
    categoria INT NOT NULL,
    categoria_nombre VARCHAR(MAX) NOT NULL,
    FOREIGN KEY (categoria) REFERENCES productos_categorias(id)
);

CREATE TABLE productos_stock (

    id INT PRIMARY KEY IDENTITY(1,1),
    categoria INT NOT NULL,
    categoria_nombre VARCHAR(MAX) NOT NULL,
    producto INT NOT NULL,
    producto_nombre VARCHAR(MAX) NOT NULL,
    cantidad INT NOT NULL,
    cantidad_custodia INT NOT NULL,
    marca VARCHAR(MAX),
    modelo VARCHAR(MAX),
    detalle VARCHAR(MAX),
    tipo VARCHAR(255) NOT NULL,
    fecha_de_carga DATETIME NOT NULL,
    consumible BIT NOT NULL DEFAULT 0,
    updated DATETIME NOT NULL,
    FOREIGN KEY (categoria) REFERENCES productos_categorias(id),
    FOREIGN KEY (producto) REFERENCES productos(id)
);

INSERT INTO productos_categorias (nombre) VALUES ('Servicios');
INSERT INTO productos_categorias (nombre) VALUES ('Alimentos Y Bebidas');
INSERT INTO productos_categorias (nombre) VALUES ('Libreria');
INSERT INTO productos_categorias (nombre) VALUES ('Limpieza');
INSERT INTO productos_categorias (nombre) VALUES ('Ferreteria');
INSERT INTO productos_categorias (nombre) VALUES ('Automotores');
INSERT INTO productos_categorias (nombre) VALUES ('Indumentaria');
INSERT INTO productos_categorias (nombre) VALUES ('Farmacia');
INSERT INTO productos_categorias (nombre) VALUES ('Muebles');
INSERT INTO productos_categorias (nombre) VALUES ('Informatica');

ALTER TABLE productos_categorias
    ADD fecha_de_carga DATETIME,
    updated DATETIME;

ALTER TABLE productos
    ADD fecha_de_carga DATETIME,
    updated DATETIME;

CREATE TABLE productos_stock_flujo (
    id INT IDENTITY(1,1) PRIMARY KEY,
    producto_stock INT NOT NULL,
    cantidad BIGINT NOT NULL,
    total BIGINT NOT NULL,
    total_legajo_custodia BIGINT,
    tipo VARCHAR(20) NOT NULL,
    empleado_custodia BIGINT,
    empleado_carga BIGINT NOT NULL,
    remito NVARCHAR(255),
    orden_de_compra NVARCHAR(255),
    precio DECIMAL(18,2),
    observaciones NVARCHAR(MAX),
    motivo_baja NVARCHAR(MAX),
    fecha DATETIME NOT NULL DEFAULT GETDATE(),

    CONSTRAINT FK_StockFlujo_Stock FOREIGN KEY (producto_stock)
        REFERENCES productos_stock(id),

    CONSTRAINT FK_StockFlujo_Empleado FOREIGN KEY (empleado_carga)
        REFERENCES rrhhempleado(legajo),

    CONSTRAINT FK_StockFlujo_Empleado_Custodia FOREIGN KEY (empleado_custodia)
        REFERENCES rrhhempleado(legajo),

    CONSTRAINT FK_StockFlujo_Custodia FOREIGN KEY (empleado_custodia)
        REFERENCES rrhhempleado(legajo)
);

CREATE TABLE productos_numeros_de_serie (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_producto_flujo INT NOT NULL,
    numero_de_serie NVARCHAR(255) NOT NULL,
    empleado_custodia BIGINT,
    activo BIT NOT NULL DEFAULT 1

    CONSTRAINT FK_NumeroSerie_ProductoFlujo FOREIGN KEY (id_producto_flujo)
        REFERENCES productos_stock_flujo(id),

    CONSTRAINT FK_NumeroSerie_Custodia FOREIGN KEY (empleado_custodia)
        REFERENCES rrhhempleado(legajo)
);