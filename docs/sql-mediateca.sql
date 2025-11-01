-- Crear base de datos
CREATE DATABASE mediateca;
USE mediateca;

-- TABLA: material
CREATE TABLE material (
  id_material VARCHAR(8) NOT NULL PRIMARY KEY,
  titulo VARCHAR(200) NOT NULL,
  tipo ENUM('Libro', 'Revista', 'CD', 'DVD') NOT NULL,
  unidades_disponibles INT DEFAULT 0,
  creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- TABLA: libro (CON TODOS LOS CAMPOS ACTUALIZADOS)
CREATE TABLE libro (
  id_material VARCHAR(8) NOT NULL PRIMARY KEY,
  autor VARCHAR(150) NOT NULL,
  num_paginas INT NOT NULL,
  editorial VARCHAR(150) NOT NULL,
  isbn VARCHAR(20),
  anio_publicacion YEAR NOT NULL,
  unidades_disponibles INT NOT NULL DEFAULT 1,
  FOREIGN KEY (id_material) REFERENCES material(id_material) ON DELETE CASCADE
) ENGINE=InnoDB;

-- TABLA: revista 
CREATE TABLE revista (
  id_material VARCHAR(8) NOT NULL PRIMARY KEY,
  editorial VARCHAR(150) NOT NULL,
  periodicidad VARCHAR(50) NOT NULL,
  fecha_publicacion DATE NOT NULL,
  unidades_disponibles INT NOT NULL DEFAULT 1,
  FOREIGN KEY (id_material) REFERENCES material(id_material) ON DELETE CASCADE
) ENGINE=InnoDB;

-- TABLA: cd 
CREATE TABLE cd (
  id_material VARCHAR(8) NOT NULL PRIMARY KEY,
  artista VARCHAR(150) NOT NULL,
  genero VARCHAR(80) NOT NULL,
  duracion INT NOT NULL,
  num_canciones INT NOT NULL,
  unidades_disponibles INT NOT NULL DEFAULT 1,
  FOREIGN KEY (id_material) REFERENCES material(id_material) ON DELETE CASCADE
) ENGINE=InnoDB;

-- TABLA: dvd
CREATE TABLE dvd (
  id_material VARCHAR(8) NOT NULL PRIMARY KEY,
  director VARCHAR(150) NOT NULL,
  duracion INT NOT NULL,
  genero VARCHAR(80) NOT NULL,
  unidades_disponibles INT NOT NULL DEFAULT 1,
  FOREIGN KEY (id_material) REFERENCES material(id_material) ON DELETE CASCADE
) ENGINE=InnoDB;

-- DATOS DE PRUEBA ACTUALIZADOS
INSERT INTO material (id_material, titulo, tipo, unidades_disponibles) VALUES 
('LIB00001', 'Cien años de soledad', 'Libro', 5),
('LIB00002', '1984', 'Libro', 3),
('REV00001', 'National Geographic', 'Revista', 2),
('CDA00001', 'Thriller', 'CD', 4),
('DVD00001', 'El Padrino', 'DVD', 2);

INSERT INTO libro (id_material, autor, num_paginas, editorial, isbn, anio_publicacion, unidades_disponibles) VALUES 
('LIB00001', 'Gabriel Garcia Marquez', 417, 'Sudamericana', '978-8437604947', 1967, 5),
('LIB00002', 'George Orwell', 328, 'Secker & Warburg', '978-0451524935', 1949, 3);

INSERT INTO revista (id_material, editorial, periodicidad, fecha_publicacion, unidades_disponibles) VALUES 
('REV00001', 'National Geographic Society', 'Mensual', '2024-01-01', 2);

INSERT INTO cd (id_material, artista, genero, duracion, num_canciones, unidades_disponibles) VALUES 
('CDA00001', 'Michael Jackson', 'Pop', 42, 9, 4);

INSERT INTO dvd (id_material, director, duracion, genero, unidades_disponibles) VALUES 
('DVD00001', 'Francis Ford Coppola', 175, 'Drama', 2);
