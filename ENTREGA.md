# Sistema de Adopción de Veterinaria - Parcial 2

## Alumno: Andrei Kobyshev
## Carrera: Analista en Sistemas
## Asignatura: Programación Avanzada
## Año: 2025
## Grupo: ACN3AP

## Resumen de Implementación

### ✅ Requisitos Cumplidos

#### 1. **Login y Registro de Empleados**
- Sistema de autenticación con base de datos H2
- Contraseñas encriptadas con SHA-256
- Patrón Singleton en `SesionServicio` para mantener el empleado logueado
- Pantalla de login y registro funcionales

#### 2. **Registro de Adopciones**
- Formulario completo para registrar adoptantes
- Selección de mascotas disponibles
- Vinculación automática con el empleado logueado
- Recomendaciones de cuidado específicas por tipo de mascota
- Generación de ticket visual en pantalla

#### 3. **Persistencia con H2**
- DAOs implementados para todas las entidades:
  - `EmpleadoDAO`
  - `MascotaDAO`
  - `AdoptanteDAO`
  - `AdopcionDAO`
- Base de datos embebida que se crea automáticamente
- Datos de prueba incluidos

#### 4. **Arquitectura MVC**
- **Modelo**: Clases de dominio sin lógica de presentación
- **Vista**: Interfaces Swing (VentanaLogin, VentanaPrincipal, VentanaTicket)
- **Controlador**: LoginControlador, AdopcionControlador
- Separación clara de responsabilidades

#### 5. **Proyecto Maven**
- Estructura estándar Maven
- Dependencias correctamente configuradas
- Ejecutable con `mvn exec:java`

### 📝 Comentarios del Profesor Atendidos
1. ✅ **Interfaces Cosa y Mascota implementadas**
   - `Cosa`: interfaz base para objetos contenibles
   - `MascotaI`: extiende Cosa con comportamiento de mascota
   - `Mascota` implementa `MascotaI`

2. ✅ **Sin transición entre estados**
   - Los estados no implementan transiciones
   - Simplificado según indicaciones

3. ✅ **Encapsulamiento mejorado**
   - No se usa equals con enums
   - Métodos tell don't ask respetados

### 🚀 Características Adicionales
- Mascotas de prueba precargadas
- Validaciones completas en todos los formularios
- Las mascotas adoptadas se marcan como no disponibles (no se eliminan)
- Manejo robusto de excepciones
- Logging configurado con SLF4J

### 📁 Estructura del Proyecto
```
src/main/java/ar/edu/davinci/
├── Main.java                    # Punto de entrada
├── controlador/                 # Controladores MVC
│   ├── LoginControlador.java
│   └── AdopcionControlador.java
├── dao/                        # Capa de persistencia
│   ├── ConexionBD.java
│   ├── EmpleadoDAO.java
│   ├── MascotaDAO.java
│   ├── AdoptanteDAO.java
│   └── AdopcionDAO.java
├── modelo/                     # Clases de dominio
│   ├── Empleado.java
│   ├── Mascota.java
│   ├── Adoptante.java
│   ├── Adopcion.java
│   ├── Cosa.java              # Interfaz solicitada
│   └── MascotaI.java          # Interfaz solicitada
├── servicio/                   # Servicios
│   └── SesionServicio.java    # Singleton para sesión
├── util/                       # Utilidades
│   └── EncriptadorContrasena.java
└── vista/                      # Interfaces gráficas
    ├── VentanaLogin.java
    ├── VentanaRegistro.java
    ├── VentanaPrincipal.java
    └── VentanaTicket.java
```

### 🔐 Credenciales de Acceso
- **Email**: admin@veterinaria.com
- **Contraseña**: admin123

### 🎯 Cómo Ejecutar
```bash
# Compilar
mvn clean compile

# Ejecutar
mvn exec:java

# O ejecutar el JAR
java -jar target/sistema-adopcion-veterinaria-2.0.0.jar
```

### 📊 Pruebas Realizadas
- ✅ Login exitoso con credenciales válidas
- ✅ Registro de nuevo empleado
- ✅ Registro de nueva mascota
- ✅ Proceso completo de adopción
- ✅ Generación de ticket
- ✅ Persistencia en base de datos

### 🎨 Capturas de Pantalla
(Las capturas se pueden tomar al ejecutar la aplicación)

1. **Pantalla de Login**: Sistema de autenticación
2. **Pantalla Principal**: Con pestañas para las diferentes funciones
3. **Registro de Mascota**: Formulario simple y funcional
4. **Proceso de Adopción**: Formulario completo con validaciones
5. **Ticket de Adopción**: Resumen detallado con recomendaciones

### 📋 Notas Finales
- El código está completamente en español como se solicitó
- Se respetaron los principios SOLID
- La aplicación es robusta y maneja errores apropiadamente
- El sistema es escalable y mantenible

---
**Entrega lista para evaluación**