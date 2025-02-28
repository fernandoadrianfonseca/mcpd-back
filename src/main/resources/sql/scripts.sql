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
     cantidad INT NOT NULL,
     precio BIGINT NOT NULL,
     marca VARCHAR(MAX),
     modelo VARCHAR(MAX),
     detalle VARCHAR(MAX),
     unidades BIGINT NOT NULL,
     numero_de_serie VARCHAR(MAX),
     fecha_de_carga DATETIME NOT NULL,
     tipo VARCHAR(255) NOT NULL,
     orden_de_compra VARCHAR(MAX),
     remito VARCHAR(MAX),
     custodia BIGINT NOT NULL DEFAULT 0,
     acta BIGINT NOT NULL DEFAULT 0,
     transfiere BIGINT NOT NULL DEFAULT 0,
     motivo_baja VARCHAR(MAX),
     fecha_de_devolucion VARCHAR(MAX),
     observaciones VARCHAR(MAX),
     FOREIGN KEY (categoria) REFERENCES productos_categorias(id)
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