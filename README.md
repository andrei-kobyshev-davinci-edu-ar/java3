# Sistema de Adopción de Veterinaria - Entrega Final

## Alumno: Andrei Kobyshev
## Carrera: Analista en Sistemas
## Asignatura: Programación Avanzada
## Año: 2025
## Grupo: ACN3AP

## Descripción del Proyecto

Sistema integral de gestión de adopciones para la veterinaria "Patitas Felices". Permite a los empleados registrados gestionar completamente el proceso de adopción de mascotas, incluyendo el registro de mascotas, procesamiento de adopciones, y gestión completa del historial de adopciones con todas las operaciones CRUD.

## Características Principales

- **Autenticación de empleados**: Sistema seguro de login con contraseñas encriptadas SHA-256
- **Gestión de mascotas**: Registro y visualización de mascotas disponibles para adopción
- **Proceso de adopción**: Registro completo de adoptantes y procesamiento de adopciones
- **Gestión completa de adopciones**: 
  - Visualizar historial completo de adopciones
  - Modificar datos de adoptantes en adopciones existentes
  - Eliminar adopciones con confirmación
  - Ver detalles completos de cada adopción
- **Generación de tickets**: Comprobantes detallados con recomendaciones personalizadas de cuidado
- **Persistencia de datos**: Base de datos relacional H2 embebida con integridad referencial

## Tecnologías Utilizadas

- **Java 11+**
- **Swing** (Interfaz gráfica)
- **H2 Database** (Base de datos embebida)
- **Maven** (Gestión de dependencias)
- **SHA-256** (Encriptación de contraseñas)

## Arquitectura

El proyecto sigue el patrón **MVC (Model-View-Controller)** y aplica principios SOLID:

### Estructura de Paquetes

```
ar.edu.davinci
├── controlador/         # Controladores (delegación a servicios)
├── dao/                 # Data Access Objects (patrón DAO con herencia)
├── excepciones/         # Excepciones específicas del dominio
├── factory/             # Factory pattern para creación de mascotas
├── modelo/              # Entidades y lógica de negocio
├── servicio/            # Servicios de negocio
├── util/                # Utilidades (encriptación)
└── vista/               # Interfaces gráficas Swing
```

## Patrones de Diseño Implementados

- **Singleton**: Para gestión de sesión y conexión a BD
- **DAO (Data Access Object)**: Para acceso a datos con BaseDAO genérico
- **Factory Method**: Para creación de mascotas sin switches
- **State**: Para estados de salud de mascotas
- **MVC**: Arquitectura general de la aplicación
- **Tell, Don't Ask**: Eliminación de switches mediante polimorfismo

## Requisitos del Sistema

- Java 11 o superior
- Maven 3.6 o superior
- 100 MB de espacio en disco

## Instrucciones de Compilación y Ejecución

### Compilar el proyecto

```bash
mvn clean compile
```

### Ejecutar tests

```bash
mvn test
```

### Empaquetar la aplicación

```bash
mvn package
```

### Ejecutar la aplicación

```bash
java -jar target/sistema-adopcion-veterinaria-2.0.0.jar
```

O alternativamente:

```bash
mvn exec:java -Dexec.mainClass="ar.edu.davinci.Main"
```

## Base de Datos

El sistema utiliza **H2 Database** en modo embebido. La base de datos se crea automáticamente en el directorio `./data/veterinaria`.

### Configuración de H2

- **URL**: `jdbc:h2:./data/veterinaria;DB_CLOSE_DELAY=-1`
- **Usuario**: `sa`
- **Contraseña**: (vacía)

### Tablas

1. **empleados**: Almacena información de empleados del sistema
2. **mascotas**: Registro de mascotas disponibles
3. **adoptantes**: Información de personas que adoptan
4. **adopciones**: Registro de adopciones realizadas

## Usuario de Prueba

El sistema incluye un usuario administrador por defecto:

- **Email**: admin@veterinaria.com
- **Contraseña**: admin123

## Funcionalidades por Ventana

### Ventana de Login
- Autenticación de empleados
- Opción de registro de nuevos empleados

### Ventana Principal
- **Pestaña Mascotas**: Lista de mascotas disponibles
- **Pestaña Registrar Mascota**: Formulario para agregar nuevas mascotas
- **Pestaña Procesar Adopción**: Registro de nuevas adopciones
- **Pestaña Gestionar Adopciones**: Acceso a la gestión completa

### Ventana de Gestión de Adopciones
- Tabla con todas las adopciones realizadas
- Ver detalles completos de cada adopción
- Modificar datos del adoptante
- Eliminar adopciones
- Generar tickets de adopción

## ✅ Implementación de Correcciones del Profesor

1. **Interfaces para abstracciones estructurales**
   - Creada interfaz genérica `DAO<T, ID>` para todos los DAOs
   - Mantenidas interfaces del dominio: `Cosa`, `MascotaI`, `DescribableI`
   - Separación clara entre contratos y implementaciones

2. **Eliminación de código repetido en DAOs**
   - Creada superclase abstracta `BaseDAO<T, ID>` con implementación común
   - Métodos template para operaciones CRUD genéricas
   - Cada DAO concreto solo implementa lo específico de su entidad
   - Reducción significativa de código duplicado

3. **Responsabilidad de crear tablas en DAOs**
   - Método `crearTabla()` en interfaz DAO
   - Cada DAO define su SQL de creación de tabla
   - Separación de responsabilidades respetada
   - ConexionBD solo coordina la inicialización

4. **Excepciones específicas para cada caso**
   - `BaseDatosException`: Errores de base de datos
   - `EntidadNoEncontradaException`: Cuando no se encuentra una entidad
   - `EmailDuplicadoException`: Email ya registrado
   - `SesionNoActivaException`: Operaciones sin sesión activa
   - `TipoMascotaInvalidoException`: Tipo de mascota inválido
   - `NombreInvalidoException`: Validación de nombres
   - No se reciclan excepciones genéricas

5. **Delegación del controlador abstraída**
   - Creado `AdopcionServicio` que encapsula toda la lógica de negocio
   - `AdopcionControlador` solo coordina entre vista y servicio
   - Separación clara de responsabilidades (SRP)
   - Mejor testabilidad y mantenibilidad

6. **VentanaTicket usa instancia de Ticket**
   - Creada clase `Ticket` en el modelo con lógica de generación
   - `VentanaTicket` solo se encarga de mostrar, no de generar
   - Encapsulación mejorada y reusabilidad del modelo

7. **Eliminación de switches (Tell Don't Ask)**
   - Creado `MascotaFactory` con patrón Factory usando Map
   - Sin switches en creación de mascotas
   - Polimorfismo en lugar de condicionales
   - Estados manejan sus propias transiciones

## Estructura Completa del Proyecto

```
src/main/java/ar/edu/davinci/
├── Main.java                           # Punto de entrada
├── controlador/
│   ├── AdopcionControlador.java        # Controlador principal (delega a servicio)
│   └── LoginControlador.java           # Controlador de autenticación
├── dao/
│   ├── DAO.java                        # Interfaz genérica para DAOs
│   ├── BaseDAO.java                    # Superclase abstracta para DAOs
│   ├── ConexionBD.java                 # Singleton para conexión
│   ├── EmpleadoDAO.java                # DAO de empleados
│   ├── MascotaDAO.java                 # DAO de mascotas
│   ├── AdoptanteDAO.java               # DAO de adoptantes
│   └── AdopcionDAO.java                # DAO de adopciones
├── excepciones/
│   ├── AdopcionException.java          # Excepción base
│   ├── BaseDatosException.java         # Errores de BD
│   ├── DatosInvalidosException.java    # Datos inválidos
│   ├── EmailDuplicadoException.java    # Email duplicado
│   ├── EntidadNoEncontradaException.java # Entidad no encontrada
│   ├── NombreInvalidoException.java    # Nombre inválido
│   ├── SesionNoActivaException.java    # Sin sesión activa
│   └── TipoMascotaInvalidoException.java # Tipo de mascota inválido
├── factory/
│   └── MascotaFactory.java             # Factory para crear mascotas
├── modelo/
│   ├── Empleado.java                   # Entidad empleado
│   ├── Adoptante.java                  # Entidad adoptante
│   ├── Adopcion.java                   # Entidad adopción
│   ├── Ticket.java                     # Modelo de ticket
│   ├── Cosa.java                       # Interfaz base
│   ├── MascotaI.java                   # Interfaz mascota
│   ├── DescribableI.java               # Interfaz describible
│   ├── Mascota.java                    # Clase abstracta mascota
│   ├── Perro.java                      # Implementación concreta
│   ├── Gato.java                       # Implementación concreta
│   ├── Conejo.java                     # Implementación concreta
│   ├── Pajaro.java                     # Implementación concreta
│   ├── EstadoMascota.java              # Patrón State
│   ├── EstadoSaludable.java            # Estado saludable
│   ├── EstadoRequiereCuidadosEspeciales.java # Requiere cuidados
│   ├── EstadoEnObservacion.java        # En observación
│   ├── EstadoConCuidadosBase.java      # Base para estados con cuidados
│   ├── RegistroCuidados.java           # Registro de cuidados
│   └── Tipos (TipoEstado, TipoCuidado, etc.) # Enums del dominio
├── servicio/
│   ├── SesionServicio.java             # Singleton para sesión
│   └── AdopcionServicio.java           # Lógica de negocio de adopciones
├── util/
│   └── EncriptadorContrasena.java      # Utilidad de encriptación
└── vista/
    ├── VentanaLogin.java               # Login del sistema
    ├── VentanaRegistro.java            # Registro de empleados
    ├── VentanaPrincipal.java           # Ventana principal con pestañas
    ├── VentanaTicket.java              # Visualización de tickets
    ├── VentanaGestionAdopciones.java   # Gestión de adopciones
    ├── VentanaDetalleAdopcion.java     # Detalle de adopción
    └── VentanaModificarAdopcion.java   # Modificar adopción
```

## ✅ Cumplimiento de Requisitos del Final

### Extensión del Sistema - Gestión Completa de Adopciones

1. **Visualizar Adopciones Existentes**
   - Nueva ventana `VentanaGestionAdopciones` con tabla completa de adopciones
   - Muestra: ID, Nombre Mascota, Tipo, Adoptante, Empleado, Fecha
   - Doble clic para ver detalles completos
   - Actualización automática al realizar cambios

2. **Modificar Adopciones**
   - Ventana `VentanaModificarAdopcion` para editar datos del adoptante
   - Validaciones en tiempo real
   - Solo se pueden modificar datos del adoptante (no mascota ni empleado)
   - Cambios persisten inmediatamente en BD

3. **Eliminar Adopciones**
   - Confirmación antes de eliminar
   - Manejo de integridad referencial
   - Feedback visual del resultado
   - Actualización automática de la lista

4. **Ver Detalles de Adopción**
   - Ventana `VentanaDetalleAdopcion` con información completa
   - Generación de ticket desde la vista de detalles
   - Muestra recomendaciones específicas según tipo de mascota

### Requisitos Técnicos Cumplidos

- **Persistencia Total**: Todas las operaciones se reflejan inmediatamente en BD H2
- **Patrón MVC**: Separación clara entre modelo, vista y controlador
- **Principios SOLID**: Aplicados en toda la arquitectura
- **Manejo de Errores**: Excepciones específicas con mensajes claros al usuario
- **Validaciones**: En todas las operaciones críticas

## 🏆 Principios y Patrones Aplicados

- **SOLID**: Todos los principios respetados
- **DRY**: Sin código repetido gracias a BaseDAO y herencia
- **Tell Don't Ask**: Eliminación de switches mediante polimorfismo
- **Separation of Concerns**: MVC + Capa de Servicios
- **Factory Method**: Para crear mascotas sin condicionales
- **State**: Para estados de salud de mascotas
- **Singleton**: Para sesión y conexión BD
- **DAO**: Con interfaz genérica y template method
- **Template Method**: En BaseDAO para operaciones comunes

## 📋 Notas de Implementación

- Todas las correcciones del profesor fueron implementadas
- Código completamente en español
- Arquitectura escalable y mantenible
- Manejo robusto de errores con feedback al usuario
- Interfaz intuitiva y completa
- Base de datos con integridad referencial
- Documentación completa del código

## Pruebas Realizadas

- ✅ Login exitoso con credenciales válidas
- ✅ Registro de nuevo empleado con validaciones
- ✅ Registro de mascotas de todos los tipos
- ✅ Proceso completo de adopción
- ✅ Visualización de todas las adopciones
- ✅ Modificación de datos de adoptante
- ✅ Eliminación de adopción con confirmación
- ✅ Generación de tickets desde detalle
- ✅ Persistencia verificada en todas las operaciones
- ✅ Manejo de errores y casos límite

---

**Repositorio GitHub**: Por favor agregue al profesor como colaborador (@underabloodysky)

**Entrega Final Lista para Evaluación**