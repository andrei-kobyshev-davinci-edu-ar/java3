# Sistema de Adopción de Veterinaria

## Parcial 2 - Programación Avanzada

### Descripción
Sistema de gestión de adopciones para una veterinaria con interfaz gráfica Swing, persistencia en H2 y arquitectura MVC.

### Requisitos
- Java 11 o superior
- Maven 3.6 o superior

### Instalación y Ejecución

1. **Compilar el proyecto:**
```bash
mvn clean compile
```

2. **Ejecutar la aplicación:**
```bash
mvn exec:java
```

3. **Ejecutar desde JAR:**
```bash
mvn package
java -jar target/sistema-adopcion-veterinaria-2.0.0.jar
```

### Credenciales de Prueba
- **Email:** admin@veterinaria.com
- **Contraseña:** admin123

### Características Implementadas

#### Arquitectura MVC
- **Modelo:** Clases de dominio (Empleado, Mascota, Adoptante, Adopción)
- **Vista:** Interfaces Swing (VentanaLogin, VentanaPrincipal, VentanaTicket)
- **Controlador:** Lógica de negocio (LoginControlador, AdopcionControlador)

#### Persistencia
- Base de datos H2 embebida
- Patrón DAO para todas las entidades
- Transacciones automáticas

#### Funcionalidades
1. **Login y Registro de Empleados**
   - Contraseñas encriptadas con SHA-256
   - Validación de credenciales contra BD

2. **Gestión de Mascotas**
   - Registro de nuevas mascotas
   - Listado de mascotas disponibles
   - Tipos soportados: Perro, Gato, Conejo, Pájaro

3. **Proceso de Adopción**
   - Registro de datos del adoptante
   - Selección de mascota disponible
   - Generación de ticket con recomendaciones

4. **Generación de Tickets**
   - Visualización en pantalla
   - Impresión directa
   - Recomendaciones específicas por tipo de mascota

### Estructura del Proyecto
```
src/main/java/ar/edu/davinci/
├── Main.java                 # Punto de entrada
├── controlador/              # Controladores MVC
├── dao/                      # Capa de persistencia
├── modelo/                   # Clases de dominio
├── servicio/                 # Servicios (Sesión)
├── util/                     # Utilidades
└── vista/                    # Interfaces Swing
```

### Base de Datos
La base de datos H2 se crea automáticamente en el directorio `data/` con las siguientes tablas:
- empleados
- mascotas
- adoptantes
- adopciones

### Notas de Implementación
- Patrón Singleton eliminado del modelo (ahora en SesionServicio)
- Las mascotas adoptadas no se eliminan, solo se marcan como no disponibles
- Validaciones completas en todos los formularios
- Manejo robusto de excepciones