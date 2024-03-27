-- Generado por Oracle SQL Developer Data Modeler 23.1.0.087.0806
--   en:        2024-03-26 15:25:03 CST
--   sitio:      Oracle Database 21c
--   tipo:      Oracle Database 21c



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE blogs (
    id          INTEGER NOT NULL,
    title       VARCHAR2(50) NOT NULL,
    description VARCHAR2(4000) NOT NULL
);

ALTER TABLE blogs ADD CONSTRAINT blogs_pk PRIMARY KEY ( id );

CREATE TABLE blogs_readers (
    readers_id INTEGER NOT NULL,
    blogs_id   INTEGER NOT NULL
);

ALTER TABLE blogs_readers ADD CONSTRAINT blogs_readers_pk PRIMARY KEY ( readers_id,
                                                                        blogs_id );

CREATE TABLE readers (
    id   INTEGER NOT NULL,
    name VARCHAR2(50) NOT NULL
);

ALTER TABLE readers ADD CONSTRAINT readers_pk PRIMARY KEY ( id );

CREATE TABLE secuencias (
    id     INTEGER NOT NULL,
    nombre VARCHAR2(20) NOT NULL,
    valor  NUMBER(6) NOT NULL
);

ALTER TABLE secuencias ADD CONSTRAINT secuencias_pk PRIMARY KEY ( id );

ALTER TABLE blogs_readers
    ADD CONSTRAINT blogs_readers_blogs_fk FOREIGN KEY ( blogs_id )
        REFERENCES blogs ( id );

ALTER TABLE blogs_readers
    ADD CONSTRAINT blogs_readers_readers_fk FOREIGN KEY ( readers_id )
        REFERENCES readers ( id );



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             4
-- CREATE INDEX                             0
-- ALTER TABLE                              6
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
