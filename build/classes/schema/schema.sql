SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS Critica;
DROP TABLE IF EXISTS Espectaculo;
DROP TABLE IF EXISTS Sesion;
DROP TABLE IF EXISTS Valoracion_critica;
DROP TABLE IF EXISTS Log;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE Usuario ( 
	nick VARCHAR(64) PRIMARY KEY,
    email VARCHAR(64) UNIQUE NOT NULL,
    nombre_y_apellidos varchar(128) NOT NULL,
    es_admin BOOLEAN NOT NULL DEFAULT FALSE,
    password VARCHAR(256) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL
);

CREATE TABLE Espectaculo ( 
	titulo VARCHAR(64) PRIMARY KEY,
    categoria varchar(64) NOT NULL,
    descripcion varchar(1024) NOT NULL,
    tipo_espectaculo ENUM("PUNTUAL", "MULTIPLE", "TEMPORADA") NOT NULL
);

CREATE TABLE Critica (
    titulo_espectaculo VARCHAR(64) NOT NULL,
    puntuacion FLOAT NOT NULL,
    comentario VARCHAR(1024) NOT NULL,
    nick_autor VARCHAR(64) NOT NULL,
    PRIMARY KEY(titulo_espectaculo, nick_autor),
    FOREIGN KEY Critica_Usuario (nick_autor) REFERENCES Usuario (nick),
    FOREIGN KEY Critica_Espectaculo (titulo_espectaculo) REFERENCES Espectaculo (titulo)
);

CREATE TABLE Valoracion_critica (
	titulo_valoracion VARCHAR(64) NOT NULL,
    nick_valoracion VARCHAR(64) NOT NULL,
    nick_a_valorar VARCHAR(64) NOT NULL,
    valoracion FLOAT NOT NULL,
    PRIMARY KEY(titulo_valoracion, nick_valoracion, nick_a_valorar),
    FOREIGN KEY Valoracion_critica_Usuario (nick_valoracion) REFERENCES Usuario (nick),
    FOREIGN KEY Valoracion_critica_Critica (titulo_valoracion) REFERENCES Critica (titulo_espectaculo)
);

CREATE TABLE Sesion (
	id INT PRIMARY KEY AUTO_INCREMENT,
    fecha TIMESTAMP NOT NULL,
    localidades INT NOT NULL,
    entradas_vendidas INT NOT NULL,
    espectaculo VARCHAR(64) NOT NULL,
    FOREIGN KEY Sesion_Espectaculo (espectaculo) REFERENCES Espectaculo (titulo)
);

CREATE TABLE Log (
	nick VARCHAR(64) PRIMARY KEY,
    ultima_conexion TIMESTAMP,
    FOREIGN KEY Log_Usuario (nick) REFERENCES Usuario (nick)
);