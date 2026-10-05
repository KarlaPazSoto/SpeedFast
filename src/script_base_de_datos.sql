-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS speedfast_db;
USE speedfast_db;

-- 1. Tabla de repartidores
CREATE TABLE repartidores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- 2. Tabla de pedidos
CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(100) NOT NULL,
    tipo ENUM('COMIDA','ENCOMIENDA','EXPRESS'),
    estado ENUM('PENDIENTE','EN_REPARTO','ENTREGADO')
);

-- 3. Tabla de entregas con sus relaciones (Foreign Keys)
CREATE TABLE entregas (
     id INT AUTO_INCREMENT PRIMARY KEY,
     id_pedido INT,
     id_repartidor INT,
     fecha DATE,
     hora TIME,
     CONSTRAINT fk_pedido FOREIGN KEY (id_pedido) REFERENCES pedidos(id) ON DELETE CASCADE,
     CONSTRAINT fk_repartidor FOREIGN KEY (id_repartidor) REFERENCES repartidores(id) ON DELETE CASCADE
);