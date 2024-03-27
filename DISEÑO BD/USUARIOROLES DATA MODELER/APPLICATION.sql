-- Generado por Oracle SQL Developer Data Modeler 23.1.0.087.0806
--   en:        2024-03-26 15:22:15 CST
--   sitio:      Oracle Database 21c
--   tipo:      Oracle Database 21c



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE rol (
    id          INTEGER NOT NULL,
    descripcion VARCHAR2(50) NOT NULL
);

ALTER TABLE rol ADD CONSTRAINT rol_pk PRIMARY KEY ( id );

CREATE TABLE usuario (
    id        INTEGER NOT NULL,
    usuario   VARCHAR2(50) NOT NULL,
    nombres   VARCHAR2(90),
    apellidos VARCHAR2(90),
    email     VARCHAR2(90),
    password  VARCHAR2(300) NOT NULL
);

ALTER TABLE usuario ADD CONSTRAINT usuario_pk PRIMARY KEY ( id );

CREATE TABLE usuario_rol (
    usuario_id INTEGER NOT NULL,
    rol_id     INTEGER NOT NULL
);

ALTER TABLE usuario_rol ADD CONSTRAINT usuario_rol__idx PRIMARY KEY ( usuario_id,
                                                                      rol_id );

ALTER TABLE usuario_rol
    ADD CONSTRAINT fk_ass_1 FOREIGN KEY ( usuario_id )
        REFERENCES usuario ( id );

ALTER TABLE usuario_rol
    ADD CONSTRAINT fk_ass_2 FOREIGN KEY ( rol_id )
        REFERENCES rol ( id );



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             3
-- CREATE INDEX                             0
-- ALTER TABLE                              5
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
