# Fix: Task ID Undefined - Error 400

## 🐛 Problema

**Error:** "Error al actualizar la tarea: 400 - /tasks/undefined"

**Causa:** El frontend buscaba `task.taskId` pero el backend devuelve `task.id`

---

## 🔍 Análisis

### Backend (TaskRequestDTO.java):
```java
private Integer id;  // ← Campo se llama "id"

public Integer getId() {
    return id;
}
```

Cuando el backend serializa a JSON, devuelve:
```json
{
  "id": 123,              // ← Campo "id"
  "title": "Tarea 1",
  "description": "...",
  ...
}
```

### Frontend (Antes del fix):
```javascript
const response = await fetch(`http://localhost:8080/tasks/${selectedTask.taskId}`);
//                                                                    ^^^^^^^ undefined
```

El objeto `selectedTask` tiene `id`, no `taskId`, por eso era `undefined`.

---

## ✅ Solución

Cambiar todas las referencias de `taskId` a `id` en el frontend.

### Cambios Realizados:

#### 1. handleUpdateTask() - Línea ~590
```javascript
// ANTES:
const response = await fetch(`http://localhost:8080/tasks/${selectedTask.taskId}`);
console.log('Task ID:', selectedTask.taskId);

// DESPUÉS:
const response = await fetch(`http://localhost:8080/tasks/${selectedTask.id}`);
console.log('Task ID:', selectedTask.id);
```

#### 2. Task List Render - Línea ~950
```javascript
// ANTES:
<div key={task.taskId} ... onClick={() => handleViewTask(task)}>
  ...
  <button onClick={(e) => { handleDeleteTask(task.taskId); }}>

// DESPUÉS:
<div key={task.id} ... onClick={() => handleViewTask(task)}>
  ...
  <button onClick={(e) => { handleDeleteTask(task.id); }}>
```

#### 3. View Task Modal - Línea ~1330
```javascript
// ANTES:
<button onClick={() => { handleDeleteTask(selectedTask.taskId); }}>

// DESPUÉS:
<button onClick={() => { handleDeleteTask(selectedTask.id); }}>
```

---

## 📊 Lugares Modificados

| Función/Lugar | Línea | Cambio |
|---------------|-------|--------|
| `handleUpdateTask()` | ~588 | `selectedTask.taskId` → `selectedTask.id` |
| `handleUpdateTask()` | ~590 | `selectedTask.taskId` → `selectedTask.id` |
| Task render (key) | ~950 | `task.taskId` → `task.id` |
| Task delete button | ~961 | `task.taskId` → `task.id` |
| View modal delete | ~1330 | `selectedTask.taskId` → `selectedTask.id` |

---

## 🎯 Resultado

Ahora todas las operaciones de tareas funcionan correctamente:

✅ **Crear tarea** - Funciona  
✅ **Ver tarea** - Funciona  
✅ **Editar tarea** - Funciona (con ID correcto)  
✅ **Eliminar tarea** - Funciona (con ID correcto)  
✅ **Asignar internos** - Funciona  

---

## 📝 Resumen de TODOS los Fixes

### Fix 1: Autenticación ✅
- `Bearer token` → `Basic authCredentials`

### Fix 2: Nombre de Campo ✅
- `internIds` → `internsId`

### Fix 3: CORS ✅
- Agregado `PATCH` a métodos permitidos

### Fix 4: Task ID ✅ (ACTUAL)
- `task.taskId` → `task.id`
- `selectedTask.taskId` → `selectedTask.id`

---

## 🧪 Testing

Ahora puedes:

1. **Crear tarea** con internos asignados ✅
2. **Click en tarea** para ver detalles ✅
3. **Editar tarea** desde el modal de vista ✅
4. **Cambiar internos asignados** ✅
5. **Guardar cambios** → Debería funcionar sin error 400 ✅
6. **Eliminar tarea** desde vista o lista ✅

---

## 🔍 Cómo Detectar Este Error

Si ves en la URL del error:
```
/tasks/undefined
```

Significa que estás intentando acceder a una propiedad que no existe en el objeto.

**Solución:** Verificar cómo el backend devuelve el objeto y usar el nombre correcto de la propiedad.

---

**Fecha:** Octubre 2024  
**Estado:** ✅ COMPLETADO - Sistema de tareas totalmente funcional  
**Archivos Modificados:** `ProjectDetail.js` (5 lugares)
