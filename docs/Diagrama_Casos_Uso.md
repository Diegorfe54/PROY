# Documentación de casos de uso

## Índice

1. [Diagrama de casos de uso](#diagrama-de-casos-de-uso)
2. [Lista de casos de uso](#lista-de-casos-de-uso)
   1. [Actores](#actores)
   2. [Autenticación](#autenticación)
      - [CU1.1 Iniciar sesión](#cu11-iniciar-sesión)
      - [CU1.2 Cerrar sesión](#cu12-cerrar-sesión)
   3. [Administrar empleados](#administrar-empleados)
      - [CU2.1 Crear nuevo empleado](#cu21-crear-nuevo-empleado)
      - [CU2.2 Modificar empleado](#cu22-modificar-empleado)
      - [CU2.3 Eliminar empleado](#cu23-eliminar-empleado)
   4. [Administrar alergias](#administrar-alergias)
      - [CU3.1 Ver alergias (CU3A)](#cu31-ver-alergias-cu3a)
      - [CU3.2 Nueva alergia (CU3B)](#cu32-nueva-alergia-cu3b)
      - [CU3.3 Editar alergia (CU3C)](#cu33-editar-alergia-cu3c)
   5. [Nota: Permisos del Administrador](#nota-permisos-del-administrador)
3. [Resumen de casos de uso](#resumen-de-casos-de-uso)

---

# Diagrama de casos de uso

```mermaid
usecase-beta
    direction LR
    actor Invitado("Invitado")

    systemBoundary "Autenticación"
    	Login("CU1.1 Iniciar sesión")
    end

    Invitado a1@--> Login
    classDef invitado fill:#e5e7eb,stroke:#6b7280,stroke-width:3px,color:#111
    class Invitado invitado
    classDef fInvitado stroke:#6b7280,stroke-width:3px
    class a1 fInvitado
```

```mermaid
usecase-beta
    direction LR
    actor Usuario("Usuario")
    actor Secretariado("Secretariado")
    actor Enfermeria("Enfermeria")
    actor Administrador("Administrador")
    
    Logout("CU1.2 Cerrar sesión")

    systemBoundary "Administrar empleados"
       CrearEmp("CU2.1 Crear nuevo empleado")
       ModEmp("CU2.2 Modificar empleado")
       ElimEmp("CU2.3 Eliminar empleado")
    end

    systemBoundary "Administrar alergías"
       VerAl("CU3.1 Ver alergias")
       NuevaAl("CU3.2 Nueva alergia")
       EditAl("CU3.3 Editar alergia")
    end

    NotaAdministrador[Administrador engloba todos los CU de todos los usuarios] <<Nota>>
    
    Secretariado h1@--|> Usuario
    Enfermeria h2@--|> Usuario
    Administrador h3@--|> Usuario

    Usuario a2@--> Logout

    Secretariado s1@--> CrearEmp
    Secretariado s2@--> ModEmp
    Secretariado s3@--> ElimEmp
    %%Administrador --> CrearEmp
    %%Administrador --> ModEmp
    %%Administrador --> ElimEmp

    Enfermeria n1@--> VerAl
    Enfermeria n2@--> NuevaAl
    Enfermeria n3@--> EditAl
    %%Administrador --> VerAl
    %%Administrador --> NuevaAl
    %%Administrador --> EditAl

    Administrador d1@--> NotaAdministrador

    classDef usuario fill:#dbeafe,stroke:#2563eb,stroke-width:3px,color:#111
    classDef secretariado fill:#fde68a,stroke:#d97706,stroke-width:3px,color:#111
    classDef enfermeria fill:#bbf7d0,stroke:#059669,stroke-width:3px,color:#111
    classDef administrador fill:#ddd6fe,stroke:#7c3aed,stroke-width:3px,color:#111

    class Usuario usuario
    class Secretariado secretariado
    class Enfermeria enfermeria
    class Administrador administrador

    classDef fUsuario stroke:#2563eb,stroke-width:3px
    classDef fSecretariado stroke:#d97706,stroke-width:3px
    classDef fEnfermeria stroke:#059669,stroke-width:3px
    classDef fAdministrador stroke:#7c3aed,stroke-width:3px

    class a2 fUsuario
    class s1,s2,s3,h1 fSecretariado
    class n1,n2,n3,h2 fEnfermeria
    class d1,h3 fAdministrador
```

---

# Lista de casos de uso

## Actores

| Actor | Descripción |
|---|---|
| **Invitado** | Persona no autenticada. Es el perfil por defecto al arrancar el programa y tras cerrar sesión. |
| **Usuario** | Actor genérico: cualquier persona autenticada. Secretariado, Enfermería y Administrador heredan de él. |
| **Secretariado** | Empleado administrativo. Gestiona el personal de la clínica. |
| **Enfermería** | Empleado sanitario. Gestiona las alergias a medicamentos. |
| **Administrador** | Perfil con credenciales fijas (`admin`/`admin`, en `application.properties`). Accede a todos los CU. |

---

## Autenticación

### CU1.1 Iniciar sesión

- **Actor:** Invitado
- **Descripción:** Proceso de autenticación mediante nombre de usuario y contraseña. Si es correcto, el Invitado pasa a ser un usuario autenticado con el perfil que le corresponde.
- **Precondiciones:**
  - No hay ninguna sesión iniciada (el usuario actual es Invitado).
  - Existe el fichero `credenciales.txt` (para Secretariado y Enfermería) y `application.properties` (para Administrador).
- **Flujo principal:**
  1. El Invitado elige la opción de iniciar sesión.
  2. El sistema solicita nombre de usuario y contraseña.
  3. El Invitado los introduce.
  4. El sistema comprueba que ninguno de los dos campos esté vacío.
  5. El sistema busca las credenciales en `credenciales.txt`, cuyas líneas tienen el formato `nombreUsuario|password|nombre|perfil`. Si el usuario es `admin`, las compara con los valores cargados de `application.properties`.
  6. Si coinciden, el sistema establece como usuario actual a esa persona con su perfil (la sesión).
  7. El sistema muestra un mensaje de bienvenida con el nombre de la persona (campo `nombre`) y las opciones propias de su rol.
- **Flujos alternativos / excepciones:**
  - *Campos vacíos:* se informa del error y no se continúa con la verificación.
  - *Credenciales incorrectas:* se informa al usuario y se mantiene el perfil Invitado.
  - *Ya hay sesión iniciada:* un usuario autenticado no puede volver a hacer login hasta cerrar sesión, así que la opción no se le ofrece.
  - *Ficheros no disponibles:* se informa del error de lectura.
- **Postcondiciones:** el sistema tiene un usuario actual autenticado con perfil `secretariado`, `enfermeria` o `admin`.
- **Datos y ficheros:** `credenciales.txt`, `application.properties`.

### CU1.2 Cerrar sesión

- **Actor:** Usuario (y, por herencia, Secretariado, Enfermería y Administrador)
- **Descripción:** Finaliza la sesión del usuario autenticado y devuelve el sistema al perfil Invitado.
- **Precondiciones:** hay un usuario autenticado.
- **Flujo principal:**
  1. El usuario autenticado selecciona la opción de cerrar sesión.
  2. El sistema elimina la sesión actual.
  3. El sistema adopta de nuevo el perfil Invitado.
  4. El sistema muestra las opciones propias del Invitado (iniciar sesión).
- **Postcondiciones:** no hay usuario autenticado. Se puede volver a hacer login (CU1.1).

---

## Administrar empleados

Pueden realizarlo **Secretariado** y **Administrador**. En el diagrama, Secretariado se conecta directamente a los CU y Administrador accede a ellos mediante sus permisos globales (reflejados en la Nota de Administrador).

### CU2.1 Crear nuevo empleado

- **Actores:** Secretariado, Administrador
- **Descripción:** Da de alta a una nueva persona que trabaja en la clínica, con sus datos personales y los propios de su puesto.
- **Precondiciones:** sesión iniciada con perfil Secretariado o Administrador.
- **Flujo principal:**
  1. El actor elige la opción de crear un nuevo empleado.
  2. El sistema solicita el tipo de empleado. Cada persona tiene **un único rol**: un empleado de secretariado nunca será cirujano o enfermero.
  3. El sistema solicita todos los datos personales y los datos específicos del puesto.
  4. El sistema solicita las credenciales de acceso: nombre de usuario y contraseña.
  5. El sistema valida:
     - El nombre de usuario es **único** en el sistema.
     - Usuario y contraseña tienen **longitud no nula y no contienen espacios en blanco**.
  6. Si todo es válido, el sistema añade al empleado y **añade una nueva línea a `credenciales.txt`** con el formato `nombreUsuario|password|nombre|perfil`.
  7. El sistema confirma el alta.
- **Flujos alternativos / excepciones:**
  - *Usuario ya existente, o usuario/contraseña vacíos o con espacios:* se informa de las causas de la invalidación y **se paraliza el registro**. No se modifica ningún fichero.
- **Postcondiciones:** el empleado queda registrado y puede iniciar sesión con sus credenciales.

### CU2.2 Modificar empleado

- **Actores:** Secretariado, Administrador
- **Descripción:** Permite actualizar los datos de un empleado ya existente.
- **Precondiciones:** sesión iniciada con perfil Secretariado o Administrador, y el empleado existe.
- **Flujo principal:**
  1. El actor selecciona al empleado a modificar.
  2. El sistema muestra sus datos actuales.
  3. El actor introduce los nuevos valores.
  4. El sistema valida con las mismas reglas que CU2.1 (usuario único, sin espacios, no vacíos).
  5. El sistema actualiza los datos y la línea correspondiente de `credenciales.txt`.
- **Excepciones:** el empleado no existe, o los datos son inválidos (se informa y no se guarda nada).

### CU2.3 Eliminar empleado

- **Actores:** Secretariado, Administrador
- **Descripción:** Da de baja a un empleado del sistema.
- **Precondiciones:** sesión iniciada con perfil Secretariado o Administrador, y el empleado existe.
- **Flujo principal:**
  1. El actor selecciona al empleado a eliminar.
  2. El sistema solicita confirmación.
  3. Si se confirma, el sistema elimina al empleado y su línea de `credenciales.txt`.
  4. El sistema informa del resultado.
- **Excepciones:** el empleado no existe, o el actor cancela la confirmación (no se elimina nada).

---

## Administrar alergias

Pueden realizarlo **Enfermería** y **Administrador**. Cada alergia se almacena en un **fichero binario propio**, dentro de una subcarpeta del repositorio de alergias. Cada fichero contiene un objeto `Alergia` que a su vez contiene un objeto `Medicamento`.

### CU3.1 Ver alergias (CU3A)

- **Actores:** Enfermería, Administrador
- **Descripción:** Muestra por pantalla todas las alergias registradas, con la información completa de cada una (incluidos los datos del medicamento).
- **Precondiciones:** sesión iniciada con perfil Enfermería o Administrador.
- **Flujo principal:**
  1. El actor elige la opción de ver alergias.
  2. El sistema lee todos los ficheros binarios de la subcarpeta de alergias.
  3. El sistema los transforma en objetos `Alergia`.
  4. El sistema los **ordena de forma ascendente por identificador**.
  5. El sistema los muestra con un formato legible: idAlergia, nombre de la alergia, y nombre, principio activo y dosis máxima del medicamento.
- **Excepciones:** si no hay alergias, se informa de que el repositorio está vacío. Si un fichero no se puede leer, se informa del error.
- **Postcondiciones:** ninguna modificación en los ficheros (es solo lectura).

### CU3.2 Nueva alergia (CU3B)

- **Actores:** Enfermería, Administrador
- **Descripción:** Registra una nueva alergia a un medicamento.
- **Precondiciones:** sesión iniciada con perfil Enfermería o Administrador.
- **Flujo principal:**
  1. El actor elige la opción de crear una alergia.
  2. El sistema solicita el nombre de la alergia y los datos del medicamento: nombre, principio activo y dosis máxima.
  3. El sistema valida los valores introducidos.
  4. El sistema **calcula automáticamente el `idAlergia`** (que coincide con el `idMedicamento`) en función de las alergias existentes.
  5. El sistema crea un objeto `Alergia` que contiene un objeto `Medicamento` con esos datos.
  6. El sistema guarda el objeto en un **nuevo fichero binario** en la subcarpeta de alergias.
  7. El sistema confirma el alta.
- **Excepciones:** si algún dato es inválido, se informa y no se crea el fichero.
- **Postcondiciones:** existe un nuevo fichero binario, que será visible en CU3.1.

### CU3.3 Editar alergia (CU3C)

- **Actores:** Enfermería, Administrador
- **Descripción:** Actualiza los datos de una alergia existente.
- **Precondiciones:** sesión iniciada con perfil Enfermería o Administrador, y la alergia existe.
- **Flujo principal:**
  1. El actor selecciona la alergia a editar.
  2. El sistema muestra sus datos actuales.
  3. El actor modifica los campos permitidos: nombre de la alergia, nombre del medicamento, principio activo y dosis.
  4. El sistema valida los nuevos valores.
  5. El sistema actualiza el fichero binario correspondiente con los nuevos datos.
- **Restricción:** **nunca** se pueden modificar los identificadores (`idAlergia` / `idMedicamento`).
- **Excepciones:** la alergia no existe, o los datos son inválidos (se informa y el fichero queda intacto).

---

## Nota: Permisos del Administrador
 
> **Nota del Diagrama:** *Administrador engloba todos los CU de todos los usuarios.*

- **Descripción y alcance:** 
  A nivel de modelado UML y diagrama de casos de uso, esta nota sustituye las múltiples asociaciones individuales del actor **Administrador** hacia cada uno de los casos de uso del sistema (`CU2.1`, `CU2.2`, `CU2.3`, `CU3.1`, `CU3.2`, `CU3.3`). De esta manera se evita la sobrecarga visual de flechas cruzadas en el diagrama mientras se modela fielmente el comportamiento de la aplicación.
- **Capacidades del Administrador:**
  El Administrador es un superusuario con permisos transversales que puede ejecutar la totalidad de operaciones existentes:
  1. **Administración de empleados:** Gestionar íntegramente la plantilla clínica (alta de nuevos empleados en `CU2.1`, modificación de datos en `CU2.2` y eliminación de personal en `CU2.3`).
  2. **Administración de alergias:** Consultar el catálogo de alergias registradas (`CU3.1`), dar de alta nuevas afecciones y medicamentos (`CU3.2`) y editar registros existentes (`CU3.3`).
  3. **Control de sesión:** Al heredar del actor genérico **Usuario**, puede cerrar sesión (`CU1.2`) de forma estándar.
- **Autenticación e inicio:**
  El acceso de este perfil se valida mediante credenciales maestras fijas (`admin-name` y `admin-password`) configuradas externamente en `application.properties`. Al iniciar sesión, la interfaz le proporciona de manera unificada el menú completo con acceso directo a todas las funcionalidades del sistema.

---

## Resumen de casos de uso

| ID | Caso de uso | Paquete | Actores |
|---|---|---|---|
| [CU1.1](#cu11-iniciar-sesión) | Iniciar sesión | Autenticación | Invitado |
| [CU1.2](#cu12-cerrar-sesión) | Cerrar sesión | Autenticación | Usuario (Secretariado, Enfermería, Administrador) |
| [CU2.1](#cu21-crear-nuevo-empleado) | Crear nuevo empleado | Administrar empleados | Secretariado, Administrador |
| [CU2.2](#cu22-modificar-empleado) | Modificar empleado | Administrar empleados | Secretariado, Administrador |
| [CU2.3](#cu23-eliminar-empleado) | Eliminar empleado | Administrar empleados | Secretariado, Administrador |
| [CU3.1](#cu31-ver-alergias-cu3a) | Ver alergias | Administrar alergias | Enfermería, Administrador |
| [CU3.2](#cu32-nueva-alergia-cu3b) | Nueva alergia | Administrar alergias | Enfermería, Administrador |
| [CU3.3](#cu33-editar-alergia-cu3c) | Editar alergia | Administrar alergias | Enfermería, Administrador |
