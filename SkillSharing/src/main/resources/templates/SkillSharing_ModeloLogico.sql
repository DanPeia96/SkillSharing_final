/*------------------------------(ELIMINAR TABLAS)------------------------------*/
DROP TABLE colaboracion;
DROP TABLE oferta;
DROP TABLE solicitud;
DROP TABLE incidencia;
DROP TABLE estudiante;
DROP TABLE promotor;
DROP TABLE habilidad;



/*------------------------------(CREAR TABLA PROMOTOR)------------------------------*/
CREATE TABLE promotor(
dni_promotor        VARCHAR(9) NOT NULL,
nombre_completo     VARCHAR(100) NOT NULL,
email               VARCHAR(100) NOT NULL,
password            VARCHAR(50) NOT NULL,
CONSTRAINT pk_dni_promotor PRIMARY KEY(dni_promotor));



/*------------------------------(CREAR TABLA ESTUDIANTE)------------------------------*/
CREATE TABLE estudiante(
dni_alumno          VARCHAR(9) NOT NULL,
nombre_completo     VARCHAR(100) NOT NULL,
email               VARCHAR(100) NOT NULL,
password            VARCHAR(50) NOT NULL,
baneado             BOOLEAN DEFAULT FALSE,
CONSTRAINT pk_dni_alumno PRIMARY KEY(dni_alumno));



/*------------------------------(CREAR TABLA INCIDENCIA)------------------------------*/
CREATE TABLE incidencia(
dni_alumno          VARCHAR(9) NOT NULL,
dni_promotor        VARCHAR(9) NOT NULL,
nombre_alumno       VARCHAR(50) NOT NULL,
nombre_promotor     VARCHAR(50) NOT NULL,
descripcion         VARCHAR(500) NOT NULL,
CONSTRAINT pk_incidencia PRIMARY KEY(dni_alumno, dni_promotor),
CONSTRAINT ca_incidencia_dni_alumno FOREIGN KEY (dni_alumno) REFERENCES estudiante(dni_alumno) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT ca_incidencia_dni_promotor FOREIGN KEY (dni_promotor) REFERENCES promotor (dni_promotor) ON DELETE RESTRICT ON UPDATE CASCADE);



/*------------------------------(CREAR TABLA HABILIDAD)------------------------------*/
CREATE TABLE habilidad(
codigo_habilidad    VARCHAR(6) NOT NULL,
nombre 		          VARCHAR(100),
descripcion		      VARCHAR(500),
nivel			          VARCHAR(100),
CONSTRAINT pk_codigo_habilidad PRIMARY KEY(codigo_habilidad));



/*------------------------------(CREAR TABLA OFERTA)------------------------------*/
CREATE TABLE oferta(
codigo_oferta 		VARCHAR(6) NOT NULL,
fecha_inicio 		  DATE,
fecha_fin    		  DATE,
descripcion 		  VARCHAR(500),
dni_alumno 		    VARCHAR(9),
codigo_habilidad 	VARCHAR(6),
CONSTRAINT pk_codigo_oferta PRIMARY KEY(codigo_oferta),
CONSTRAINT ca_oferta_dni_alumno FOREIGN KEY (dni_alumno) REFERENCES estudiante(dni_alumno) ON DELETE RESTRICT ON UPDATE CASCADE,
CONSTRAINT ca_oferta_habilidad FOREIGN KEY (codigo_habilidad) REFERENCES habilidad (codigo_habilidad) ON DELETE RESTRICT ON UPDATE CASCADE);



/*------------------------------(CREAR TABLA SOLICITUD)------------------------------*/
CREATE TABLE solicitud(
codigo_solicitud 	VARCHAR(6),
fecha_inicio 		  DATE,
fecha_fin 		    DATE,
descripcion 		  VARCHAR(500),
dni_alumno 		    VARCHAR(9),
codigo_habilidad 	VARCHAR(6),
CONSTRAINT pk_codigo_solicitud PRIMARY KEY (codigo_solicitud),
CONSTRAINT ca_solicitud_dni_alumno FOREIGN KEY (dni_alumno) REFERENCES estudiante(dni_alumno) ON DELETE RESTRICT ON UPDATE CASCADE,
CONSTRAINT ca_solicitud_habilidad FOREIGN KEY (codigo_habilidad) REFERENCES habilidad(codigo_habilidad) ON DELETE RESTRICT ON UPDATE CASCADE);



/*------------------------------(CREAR TABLA COLABORACION)------------------------------*/
CREATE TABLE colaboracion(
codigo_colaboracion 	VARCHAR(6),
fecha_inicio			    DATE,
fecha_fin			        DATE,
horas 				        FLOAT,
evaluacion 			      VARCHAR(500),
codigo_oferta 			  VARCHAR(6),
codigo_solicitud 		  VARCHAR(6),
CONSTRAINT pk_codigo_colaboracion PRIMARY KEY (codigo_colaboracion),
CONSTRAINT ca_colaboracion_oferta FOREIGN KEY(codigo_oferta) REFERENCES oferta(codigo_oferta) ON DELETE RESTRICT ON UPDATE CASCADE,
CONSTRAINT ca_colaboracion_solicitud FOREIGN KEY (codigo_solicitud)REFERENCES solicitud(codigo_solicitud) ON DELETE RESTRICT ON UPDATE CASCADE); 
