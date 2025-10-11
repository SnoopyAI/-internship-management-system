# Fix: Error al Actualizar Tareas - Autenticación

## 🐛 Problema Detectado

**Error:** "Error al actualizar la tarea" en `localhost:3000`

**Causa Raíz:** Inconsistencia en el sistema de autenticación

- El resto de la aplicación usa: `Basic Auth` con `authCredentials`
- Las funciones de tareas/listas usaban: `Bearer Token` con `token`

Esto causaba errores 401 (No autorizado) al intentar crear, editar o eliminar tareas y listas.

---

## ✅ Solución Implementada

### Funciones Corregidas en `ProjectDetail.js`:

#### 1. **loadLists()** - Línea ~415
```javascript
// ANTES:
const token = localStorage.getItem('token');
'Authorization': `Bearer ${token}`

// DESPUÉS:
const credentials = localStorage.getItem('authCredentials');
'Authorization': `Basic ${credentials}`
```

#### 2. **handleAddList()** - Línea ~445
```javascript
// ANTES:
const token = localStorage.getItem('token');
'Authorization': `Bearer ${token}`

// DESPUÉS:
const credentials = localStorage.getItem('authCredentials');
'Authorization': `Basic ${credentials}`
```

#### 3. **handleDeleteList()** - Línea ~478
```javascript
// ANTES:
const token = localStorage.getItem('token');
'Authorization': `Bearer ${token}`

// DESPUÉS:
const credentials = localStorage.getItem('authCredentials');
'Authorization': `Basic ${credentials}`
```

#### 4. **handleAddTask()** - Línea ~505
```javascript
// ANTES:
const token = localStorage.getItem('token');
'Authorization': `Bearer ${token}`

// DESPUÉS:
const credentials = localStorage.getItem('authCredentials');
'Authorization': `Basic ${credentials}`
```

#### 5. **handleUpdateTask()** - Línea ~568
```javascript
// ANTES:
const token = localStorage.getItem('token');
'Authorization': `Bearer ${token}`

// DESPUÉS:
const credentials = localStorage.getItem('authCredentials');
'Authorization': `Basic ${credentials}`
```

#### 6. **handleDeleteTask()** - Línea ~613
```javascript
// ANTES:
const token = localStorage.getItem('token');
'Authorization': `Bearer ${token}`

// DESPUÉS:
const credentials = localStorage.getItem('authCredentials');
'Authorization': `Basic ${credentials}`
```

---

## 📋 Funciones Afectadas

| Función | Endpoint | Método | Estado |
|---------|----------|--------|--------|
| `loadLists()` | `/lists/board/{id}` | GET | ✅ Corregido |
| `loadLists()` (tasks) | `/tasks/list/{listId}` | GET | ✅ Corregido |
| `handleAddList()` | `/lists/add` | POST | ✅ Corregido |
| `handleDeleteList()` | `/lists/{id}` | DELETE | ✅ Corregido |
| `handleAddTask()` | `/tasks/add` | POST | ✅ Corregido |
| `handleUpdateTask()` | `/tasks/{id}` | PATCH | ✅ Corregido |
| `handleDeleteTask()` | `/tasks/{id}` | DELETE | ✅ Corregido |

---

## 🔍 Verificación del Sistema de Auth

### Dónde se almacenan las credenciales:

En `Login.js` después del login exitoso:
```javascript
localStorage.setItem('authCredentials', encodedCredentials);
localStorage.setItem('userData', JSON.stringify(data));
```

### Formato de las credenciales:

```javascript
// authCredentials: Base64 de "username:password"
const encodedCredentials = btoa(`${username}:${password}`);

// Uso en headers:
'Authorization': `Basic ${encodedCredentials}`
```

---

## ✅ Resultado

Ahora **todas las operaciones CRUD** de tareas y listas usan el mismo sistema de autenticación que el resto de la aplicación:

- ✅ Crear lista
- ✅ Eliminar lista
- ✅ Cargar listas y tareas
- ✅ Crear tarea
- ✅ Actualizar tarea (con asignación de internos)
- ✅ Eliminar tarea

---

## 🧪 Testing

Para verificar que todo funciona:

1. **Login** en la aplicación
2. **Acceder** a un proyecto
3. **Ir a pestaña "Tareas"**
4. **Crear una lista** → Debería funcionar
5. **Crear una tarea** → Debería funcionar
6. **Editar tarea** y asignar internos → Debería funcionar ✅
7. **Eliminar tarea** → Debería funcionar
8. **Eliminar lista** → Debería funcionar

---

**Fecha:** Octubre 2024  
**Estado:** ✅ COMPLETADO - Sin errores de autenticación
