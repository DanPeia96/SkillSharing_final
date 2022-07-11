DROP TABLE colaboracion;
DROP TABLE solicitud;
DROP TABLE oferta;
DROP TABLE incidencia;
DROP TABLE usuario;
DROP TABLE habilidad;

CREATE TABLE usuario(
                        id_usuario        VARCHAR(9) NOT NULL,
                        nombre_completo   VARCHAR(50) NOT NULL,
                        email             VARCHAR(50) NOT NULL,
                        password          VARCHAR(50) NOT NULL,
                        activado          BOOLEAN DEFAULT TRUE,
                        admin             BOOLEAN DEFAULT FALSE,
                        saldo_horas       FLOAT NOT NULL,
                        CONSTRAINT pk_usuario PRIMARY KEY(id_usuario)
);


CREATE TABLE incidencia(
                           id_alumno   VARCHAR(9) NOT NULL,
                           fecha       DATE NOT NULL,
                           id_promotor VARCHAR(9) NOT NULL,
                           descripcion VARCHAR(500),

                           CONSTRAINT pk_incidencia PRIMARY KEY(id_alumno, fecha),
                           CONSTRAINT ca_incidencia_id_alumno FOREIGN KEY (id_alumno) REFERENCES usuario(id_usuario) ON DELETE RESTRICT ON UPDATE CASCADE,
                           CONSTRAINT ca_incidencia_id_promotor FOREIGN KEY (id_promotor) REFERENCES usuario(id_usuario) ON DELETE RESTRICT ON UPDATE CASCADE,
                           CONSTRAINT ri_coincidencia_usuario CHECK (id_alumno <> id_promotor)
);


CREATE TABLE habilidad(
                          nombre        VARCHAR(20) NOT NULL,
                          nivel         INTEGER NOT NULL,
                          descripcion   VARCHAR(200),
                          disponible    BOOLEAN DEFAULT TRUE,

                          CONSTRAINT pk_codigo_habilidad PRIMARY KEY(nombre, nivel)
);


CREATE TABLE oferta(
                       codigo_oferta   VARCHAR(6) NOT NULL,
                       fecha_inicio    DATE NOT NULL,
                       fecha_fin       DATE,
                       id_usuario      VARCHAR(9) NOT NULL,
                       tipo            BOOLEAN NOT NULL,
                       nombre_habilidad  VARCHAR(20) NOT NULL,
                       nivel_habilidad   INTEGER NOT NULL,
                       descripcion     VARCHAR(500),


                       CONSTRAINT pk_codigo_oferta PRIMARY KEY(codigo_oferta),
                       CONSTRAINT ca_id_usuario FOREIGN KEY(id_usuario) REFERENCES usuario(id_usuario) ON DELETE RESTRICT ON UPDATE CASCADE,
                       CONSTRAINT ca_habilidad FOREIGN KEY(nombre_habilidad, nivel_habilidad) REFERENCES habilidad(nombre, nivel) ON DELETE RESTRICT ON UPDATE CASCADE,
                       CONSTRAINT ri_fini_ffin CHECK (fecha_fin>=fecha_inicio)
);


CREATE TABLE solicitud(
                          codigo_solicitud  VARCHAR(6) NOT NULL,
                          codigo_oferta    VARCHAR(6) NOT NULL,
                          id_usuario_solicitante  VARCHAR(9) NOT NULL,
                          fecha_emision       DATE NOT NULL,
                          fecha_aceptacion    DATE,
                          CONSTRAINT pk_codigo_solicitud PRIMARY KEY(codigo_solicitud),
                          CONSTRAINT ca_id_usuario FOREIGN KEY(id_usuario_solicitante) REFERENCES usuario(id_usuario) ON DELETE RESTRICT ON UPDATE CASCADE,
                          CONSTRAINT ca_oferta FOREIGN KEY(codigo_oferta) REFERENCES oferta(codigo_oferta) ON DELETE RESTRICT ON UPDATE CASCADE,
                          CONSTRAINT ri_fini_ffin CHECK (fecha_emision<=fecha_aceptacion)
);


CREATE TABLE colaboracion(
                             codigo_colaboracion   VARCHAR(6) NOT NULL,
                             codigo_solicitud  VARCHAR(6) NOT NULL,
                             fecha_inicio          DATE NOT NULL,
                             fecha_fin             DATE,
                             horas                 FLOAT NOT NULL,
                             evaluacion            INT,
                             estado                BOOLEAN DEFAULT TRUE,

                             CONSTRAINT  pk_codigo_colaboracion PRIMARY KEY(codigo_colaboracion),
                             CONSTRAINT ca_codigo_solicitud FOREIGN KEY(codigo_solicitud) REFERENCES
                                 solicitud(codigo_solicitud) ON DELETE RESTRICT ON UPDATE CASCADE,
                             CONSTRAINT ri_evaluacion CHECK (evaluacion BETWEEN 0 AND 10)
);


INSERT INTO Usuario VALUES ('A1234509', 'GEMMA MENGUAL', 'al000001@gmail.com', '1234', TRUE, FALSE, 16);
INSERT INTO Usuario VALUES ('A2345091', 'ALBUSAC TAMARGO DANIEL', 'al000002@gmail.com', '1234', TRUE, FALSE,15.5);
INSERT INTO Usuario (id_usuario, nombre_completo, email, password, saldo_horas) VALUES ('A2345098', 'TAMARGO ALBUSAC DANIEL', 'al000002@gmail.com', '1234', 15.5);
INSERT INTO Usuario VALUES ('A1345092', 'CASTELLS GALLEGO MARAI DEL TISCAR', 'al000003@gmail.com', '1234', TRUE, TRUE, 20);
INSERT INTO Usuario VALUES ('G1245093', 'CUETO AVELLANEDA RAFAEL', 'al000004@gmail.com', '1234', FALSE, FALSE, 1.5);
INSERT INTO Usuario VALUES ('R1235094', 'ESCOT HIGUERAS SANDRA', 'al000005@gmail.com', '1234', TRUE, TRUE, 19);

INSERT INTO incidencia VALUES ('G1245093', TO_DATE('21/02/2022', 'DD/MM/YYYY'), 'R1235094', 'El nivel ofertado es mayor que el que da');

INSERT INTO habilidad VALUES ('Inglés', '1', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.');
INSERT INTO habilidad VALUES ('Inglés', '2', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.');
INSERT INTO habilidad VALUES ('Inglés', '3', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.');
INSERT INTO habilidad VALUES ('Mates', '3', 'The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested.');
INSERT INTO habilidad VALUES ('Historia', '2', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour');

INSERT INTO oferta VALUES ('o00001', TO_DATE('21/05/2019', 'DD/MM/YYYY'), TO_DATE('21/06/2019', 'DD/MM/YYYY'), 'A1234509', true, 'Mates', '3', '1The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested.');
INSERT INTO oferta VALUES ('o00002', TO_DATE('21/07/2019', 'DD/MM/YYYY'), TO_DATE('29/07/2019', 'DD/MM/YYYY'), 'A1234509', true,'Inglés', '1', '2The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested.');
INSERT INTO oferta VALUES ('o00003', TO_DATE('27/04/2022', 'DD/MM/YYYY'), TO_DATE('21/08/2022', 'DD/MM/YYYY'), 'A1234509', false,'Historia', '2', '3The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested.');
INSERT INTO oferta (codigo_oferta, fecha_inicio, id_usuario, tipo, nombre_habilidad, nivel_habilidad, descripcion)  VALUES ('o00004', TO_DATE('21/05/2020', 'DD/MM/YYYY'), 'A2345091', true, 'Inglés', '3', '4The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested.');
INSERT INTO oferta VALUES ('o00005', TO_DATE('14/09/2021', 'DD/MM/YYYY'), TO_DATE('21/10/2021', 'DD/MM/YYYY'), 'A2345091', false, 'Inglés', '1', '5The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested.');


INSERT INTO solicitud VALUES ('s00001', 'o00001', 'A2345091', TO_DATE('22/05/2019', 'DD/MM/YYYY'), TO_DATE('24/05/2019', 'DD/MM/YYYY'));
INSERT INTO solicitud VALUES ('s00002', 'o00001', 'A2345098', TO_DATE('23/07/2019', 'DD/MM/YYYY'), TO_DATE('26/07/2019', 'DD/MM/YYYY'));
INSERT INTO solicitud VALUES ('s00003', 'o00002', 'A2345091', TO_DATE('28/04/2022', 'DD/MM/YYYY'), TO_DATE('21/07/2022', 'DD/MM/YYYY'));
INSERT INTO solicitud (codigo_solicitud, codigo_oferta, id_usuario_solicitante, fecha_emision) VALUES ('s00004', 'o00001', 'A1234509', TO_DATE('22/05/2020', 'DD/MM/YYYY'));
INSERT INTO solicitud VALUES ('s00005', 'o00003', 'A2345098', TO_DATE('16/09/2021', 'DD/MM/YYYY'), TO_DATE('20/10/2021', 'DD/MM/YYYY'));

INSERT INTO colaboracion VALUES ('c00001', 's00001', TO_DATE('24/05/2019', 'DD/MM/YYYY'), TO_DATE('24/05/2019', 'DD/MM/YYYY'), 2.5, 4, true);
INSERT INTO colaboracion VALUES ('c00002', 's00005', TO_DATE('26/07/2019', 'DD/MM/YYYY'), TO_DATE('28/07/2019', 'DD/MM/YYYY'), 1, 5, true);
INSERT INTO colaboracion (codigo_colaboracion, codigo_solicitud, fecha_inicio, fecha_fin, horas, estado) VALUES ('c00003', 's00002', TO_DATE('21/07/2022', 'DD/MM/YYYY'), TO_DATE('22/07/2022', 'DD/MM/YYYY'), 1.5, FALSE);
