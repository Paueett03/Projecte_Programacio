# Pràctica Base de dades

_Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas._

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

-- (Continúa con el resto de las sentencias SQL proporcionadas)
