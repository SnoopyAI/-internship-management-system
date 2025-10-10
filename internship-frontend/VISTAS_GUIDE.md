# 📚 Vistas del Sistema de Gestión de Pasantías (SGP)

## Resumen de Vistas Implementadas

Este documento describe todas las vistas del sistema de gestión de pasantías y cómo navegar entre ellas.

---

## 🎯 Vistas Principales

### 1. **Login** (`/`)
- **Descripción**: Página de inicio de sesión
- **Funcionalidad**: Autenticación de usuarios con credenciales Basic Auth
- **Navegación**: Redirige a `/dashboard` después del login exitoso
- **Estado**: ✅ Completada

### 2. **Dashboard** (`/dashboard`)
- **Descripción**: Panel principal del usuario
- **Funcionalidades**:
  - Vista general de proyectos activos
  - Creación de nuevos proyectos
  - Acceso rápido a tareas recientes
  - Estadísticas del usuario
- **Navegación**: Hub central que conecta con todas las demás vistas
- **Estado**: ✅ Completada

### 3. **Project Detail** (`/project/:id`)
- **Descripción**: Vista detallada de un proyecto específico
- **Funcionalidades**:
  - Información del proyecto
  - Gestión de participantes (tutores académicos, tutores de empresa, internos)
  - Tablero Kanban de tareas
  - Creación de listas y tareas
  - Asignación de universidades
- **Navegación**: Accesible desde las tarjetas de proyectos en Dashboard
- **Estado**: ✅ Completada

### 4. **Universities** (`/universities`)
- **Descripción**: Gestión de universidades del sistema
- **Funcionalidades**:
  - Listar todas las universidades
  - Crear nuevas universidades
  - Editar universidades existentes
  - Eliminar universidades
  - Búsqueda de universidades
- **API Endpoints**:
  - GET `/universities/ReadAll`
  - POST `/universities/add`
  - PATCH `/universities/:id`
  - DELETE `/universities/:id`
- **Estado**: ✅ Completada

### 5. **Participants** (`/participants`)
- **Descripción**: Vista unificada de todos los participantes
- **Funcionalidades**:
  - Lista completa de participantes (tutores académicos, tutores de empresa, internos)
  - Filtros por tipo de participante
  - Búsqueda por nombre o email
  - Vista de detalles de cada participante
- **API Endpoints**:
  - GET `/academytutors/ReadAll`
  - GET `/companytutors/ReadAll`
  - GET `/interns/ReadAll`
- **Estado**: ✅ Completada

### 6. **Tasks** (`/tasks`)
- **Descripción**: Vista ampliada de tareas del usuario
- **Funcionalidades**:
  - Lista de todas las tareas asignadas al usuario
  - Filtros por estado (pendiente, en progreso, completada)
  - Búsqueda de tareas
  - Priorización visual (alta, media, baja)
  - Fechas de vencimiento
- **Nota**: Por ahora usa datos de ejemplo. Conectar con endpoints reales en el futuro.
- **Estado**: ✅ Completada (con datos mock)

### 7. **Reports** (`/reports`)
- **Descripción**: Reportes y estadísticas del sistema
- **Funcionalidades**:
  - Dashboard de estadísticas clave
  - Métricas de proyectos activos y completados
  - Tareas pendientes y completadas
  - Total de participantes
  - Progreso promedio
  - Gráficos (preparado para integración futura)
- **Filtros temporales**: Semana, Mes, Año
- **Estado**: ✅ Completada (con datos mock, placeholders para gráficos)

### 8. **Settings** (`/settings`)
- **Descripción**: Configuración de la cuenta del usuario
- **Secciones**:
  - **Perfil**: Editar nombre y email
  - **Seguridad**: Cambio de contraseña
  - **Notificaciones**: Preferencias de notificaciones
  - **Apariencia**: Tema y idioma
- **Nota**: Funcionalidad básica implementada, se requiere conectar con endpoints del backend
- **Estado**: ✅ Completada (UI completa, funcionalidad pendiente)

---

## 🗺️ Mapa de Navegación

```
Login (/)
  └─> Dashboard (/dashboard)
       ├─> Project Detail (/project/:id)
       ├─> Universities (/universities)
       ├─> Participants (/participants)
       ├─> Tasks (/tasks)
       ├─> Reports (/reports)
       └─> Settings (/settings)
```

### Navegación Global (Header en todas las vistas)
Todas las vistas protegidas incluyen un header con navegación consistente:
- **Inicio** → Dashboard
- **Equipo** → Participants
- **Tareas** → Tasks
- **Universidades** → Universities
- **Reportes** → Reports
- **Configuración** → Settings

---

## 🎨 Diseño y Estilo

### Tema Visual
- **Gradiente principal**: `linear-gradient(135deg, #0f0c29 0%, #302b63 50%, #24243e 100%)`
- **Color primario**: Violeta/Púrpura (`#6366f1`, `#8b5cf6`)
- **Tipografía**: Moderna y limpia
- **Modo oscuro**: Activo por defecto en todas las vistas

### Componentes Reutilizables
- **Header Navigation**: Barra superior consistente en todas las vistas
- **Modal System**: Modales reutilizables con scroll interno y botones sticky
- **Cards**: Tarjetas con hover effects para proyectos, participantes, universidades
- **Filtros**: Sistema de filtrado consistente (tabs + search)

---

## 🚀 Cómo Probar las Vistas

1. **Iniciar el backend**:
```bash
cd bootcamp_proyect
./mvnw spring-boot:run
```

2. **Iniciar el frontend**:
```bash
cd internship-frontend
npm install
npm start
```

3. **Navegar**:
- Accede a `http://localhost:3000`
- Inicia sesión con credenciales válidas
- Explora las diferentes vistas usando el menú de navegación

---

## 📝 Tareas Pendientes (Funcionalidad Backend)

### Para completar en el futuro:
1. **Tasks**: Conectar con endpoints reales de tareas
2. **Reports**: Implementar generación real de estadísticas y gráficos
3. **Settings**: 
   - Endpoint para actualizar perfil de usuario
   - Endpoint para cambiar contraseña
   - Sistema de notificaciones
4. **Participants**: Agregar funcionalidad de edición/eliminación de participantes
5. **Universities**: Validar que no se puedan eliminar universidades con participantes asociados

---

## 🔐 Rutas Protegidas

Todas las rutas excepto `/` (Login) están protegidas mediante el componente `PrivateRoute`:
- Verifica la existencia de `userEmail` en localStorage
- Redirige automáticamente a Login si el usuario no está autenticado
- Mantiene la sesión mientras existan las credenciales en localStorage

---

## 📦 Estructura de Archivos

```
src/
├── components/
│   ├── Login.js + Login.css
│   ├── Dashboard.js + Dashboard.css
│   ├── ProjectDetail.js + ProjectDetail.css
│   ├── Universities.js + Universities.css
│   ├── Participants.js + Participants.css
│   ├── Tasks.js + Tasks.css
│   ├── Reports.js + Reports.css
│   └── Settings.js + Settings.css
├── App.js (Router principal)
└── App.css
```

---

## ✅ Checklist de Implementación

- [x] Login
- [x] Dashboard
- [x] Project Detail
- [x] Universities
- [x] Participants
- [x] Tasks
- [x] Reports
- [x] Settings
- [x] Navegación global
- [x] Rutas protegidas
- [x] Diseño responsive
- [x] Modales reutilizables
- [x] Estilos consistentes

---

**Última actualización**: Octubre 9, 2025
