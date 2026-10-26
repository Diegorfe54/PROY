
# Diagrama entidad-relación

```mermaid
erDiagram
    PACIENTE {
        BIGINT id_paciente PK
        VARCHAR(100) nombre
        DATE fecha_nacimiento
        VARCHAR(20) telefono
        VARCHAR(150) email
        VARCHAR(200) direccion
        VARCHAR(20) nif_nie
    }

    TRATAMIENTO {
        BIGINT id_tratamiento PK
        VARCHAR(150) nombre
        DATE fecha_inicio
        BOOLEAN consentimiento
        BIGINT id_paciente FK
    }

    HISTORIAL {
        BIGINT num_historia PK
        VARCHAR(1000) descripcion
        BIGINT id_paciente FK
    }

    ALERGIA {
        BIGINT id_alergia PK
        VARCHAR(150) nombre
        BIGINT num_historia FK
        BIGINT id_medicamento FK
    }

    MEDICAMENTO {
        BIGINT id_medicamento PK
        VARCHAR(150) nombre
        VARCHAR(200) principio_activo
        DECIMAL(10,2) dosis_max_diaria_mg
    }

    CITA {
        BIGINT id_cita PK
        TIMESTAMP fecha_hora
        BIGINT id_tratamiento FK
        VARCHAR(50) tipo
    }

    REVISION {
        BIGINT id_cita PK, FK
        VARCHAR(2000) anotaciones
    }

    INTERVENCION {
        BIGINT id_cita PK, FK
        INTEGER duracion_minutos
    }

    PACIENTE ||--|{ TRATAMIENTO : necesita
    PACIENTE ||--|| HISTORIAL : tiene
    HISTORIAL ||--o{ ALERGIA : contiene
    MEDICAMENTO ||--o{ ALERGIA : "esta asociada a"
    TRATAMIENTO ||--|{ CITA : "se desarrolla en"
    CITA ||--o| REVISION : "es una"
    CITA ||--o| INTERVENCION : "es una"
```