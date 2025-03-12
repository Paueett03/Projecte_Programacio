# Pràctica Base de dades
---

## Disseny Conceptual

![Disseny Conceptual](DissenyConceptual-Disseny.drawio.png)

---

## Disseny Lògic-Relacional

### Taules Principals

- **PERSONA** (`id_persona`, email, data_naixement, nom, cognom, adreça, document_identitat, telefon)
- **CLIENT** (`id_client`, data_registre, tipus_client, targeta_credit, `id_persona`)
  - `id_persona` és clau forana que fa referència a la taula PERSONA (`id_persona`)
- **RESERVA** (`id_reserva`, preu_total_reserva, data_reserva, data_inici, data_fi, tipus_reserva, Tipus_IVA, `id_factura`, `id_habitacio`, `id_client`)
  - `id_factura` és clau forana que fa referència a la taula FACTURA (`id_factura`)
  - `id_habitacio` és clau forana que fa referència a la taula HABITACIÓ (`id_habitacio`)
  - `id_client` és clau forana que fa referència a la taula CLIENT (`id_client`)
- **HABITACIÓ** (`id_habitacio`, numero_habitacio, tipus, capacitat, preu_nit_AD, preu_nit_MP, descripcio, estat)
- **EMPLEAT** (`id_empleat`, llocFeina, data_contractació, salariBrut, estat, `id_persona`)
  - `id_persona` és clau forana que fa referència a la taula PERSONA (`id_persona`)
- **FACTURA** (`id_factura`, data_emisio, metode_pagament, base_imposable, iva, total)
- **TASCA** (`id_tasca`, data_creacio, data_ejecucio, descripcio, estat, `id_empleat`)
  - `id_empleat` és clau forana que fa referència a la taula EMPLEAT (`id_empleat`)
- **ASSIG_EMPLEAT_TASCA** (`id_empleat`, `id_tasca`, estat)
  - `id_empleat` és clau forana que fa referència a la taula EMPLEAT (`id_empleat`)
  - `id_tasca` és clau forana que fa referència a la taula TASCA (`id_tasca`)

---

## UML

![UML](DissenyConceptual-UML.drawio.png)

---

## Sentències SQL: LDD

```sql
-- Creació de totes les taules
CREATE TABLE Persona (
    id_persona INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    data_naixement DATE,
    nom VARCHAR(100),
    cognom VARCHAR(100),
    adreça VARCHAR(255),
    document_identitat VARCHAR(50) UNIQUE NOT NULL,
    telefon INT
);

CREATE TABLE Empleat (
    id_empleat INT PRIMARY KEY,
    llocFeina VARCHAR(100),
    data_contractacio DATE,
    salariBrut FLOAT,
    estat_laboral VARCHAR(50),
    FOREIGN KEY (id_empleat) REFERENCES Persona(id_persona)
);

CREATE TABLE Client (
    id_client INT PRIMARY KEY,
    data_registre DATE,
    tipus_client VARCHAR(50),
    targeta_credit VARCHAR(50),
    FOREIGN KEY (id_client) REFERENCES Persona(id_persona)
);

CREATE TABLE Habitacio (
    id_habitacio INT PRIMARY KEY AUTO_INCREMENT,
    numero_habitacio INT UNIQUE NOT NULL,
    tipus VARCHAR(50),
    capacitat INT,
    preu_nit_AP DOUBLE,
    preu_nit_MP DOUBLE,
    descripcio TEXT,
    estat VARCHAR(50)
);

CREATE TABLE Reserva (
    id_reserva INT PRIMARY KEY AUTO_INCREMENT,
    id_client INT,
    preu_total_reserva DOUBLE,
    data_reserva DATE,
    data_inici DATE,
    data_fi DATE,
    tipus_reserva VARCHAR(100),
    Tipus_IVA VARCHAR(50),
    FOREIGN KEY (id_client) REFERENCES Client(id_client)
);

CREATE TABLE Factura (
    id_factura INT PRIMARY KEY AUTO_INCREMENT,
    id_reserva INT,
    base_imposable FLOAT,
    data_emisio DATE,
    metode_pagament VARCHAR(100),
    iva DOUBLE,
    total DOUBLE,
    FOREIGN KEY (id_reserva) REFERENCES Reserva(id_reserva)
);

CREATE TABLE Tasca (
    id_tasca INT PRIMARY KEY AUTO_INCREMENT,
    data_creacio DATE,
    data_execucio DATE,
    descripcio TEXT,
    estat VARCHAR(100)
);

CREATE TABLE Assig_Empleat_Tasca (
    id_empleat INT,
    id_tasca INT,
    PRIMARY KEY (id_empleat, id_tasca),
    FOREIGN KEY (id_empleat) REFERENCES Empleat(id_empleat),
    FOREIGN KEY (id_tasca) REFERENCES Tasca(id_tasca)
);

-- Inserts de proba
INSERT INTO Persona (email, data_naixement, nom, cognom, adreça, document_identitat, telefon) 
VALUES 
('johndoe@example.com', '1985-06-15', 'John', 'Doe', 'Carrer Falsa 123', '12345678X', 666777888),
('janesmith@example.com', '1990-09-20', 'Jane', 'Smith', 'Avinguda Central 45', '87654321Y', 654321987),
('michaelbrown@example.com', '1982-12-05', 'Michael', 'Brown', 'Plaça Major 78', '11223344Z', 612345678);

-- (Continúa con el resto de las sentencias SQL proporcionadas)
