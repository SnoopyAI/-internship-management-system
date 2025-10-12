# Fix Final: Error al Actualizar Tareas - Nombre de Campo

## 🐛 Problema

**Error:** "Error al actualizar la tarea" - El backend no aceptaba la actualización

**Causa Real:** Discrepancia en el nombre del campo entre Frontend y Backend

---

## 🔍 Análisis

### Backend (TaskRequestDTO.java):
```java
private List<Integer> internsId;  // ← Sin "s" al final

public List<Integer> getInternsId() {
    return internsId;
}

public void setInternsId(List<Integer> internsId) {
    this.internsId = internsId;
}
```

### Frontend (Antes del fix):
```javascript
body: JSON.stringify({
  internIds: taskForm.assignedInternIds  // ← Con "s" al final ❌
})
```

El backend esperaba `internsId` pero el frontend enviaba `internIds`, causando que el campo no se mapeara correctamente.

---

## ✅ Solución

### 1. handleAddTask() - Crear Tarea
```javascript
// ANTES:
internIds: taskForm.assignedInternIds || []  ❌

// DESPUÉS:
internsId: taskForm.assignedInternIds || []  ✅
```

### 2. handleUpdateTask() - Actualizar Tarea
```javascript
// ANTES:
internIds: taskForm.assignedInternIds || []  ❌

// DESPUÉS:
internsId: taskForm.assignedInternIds || []  ✅
```

---

## 📊 Cambios Realizados

### Archivo: `ProjectDetail.js`

#### Línea ~527 (handleAddTask):
```javascript
body: JSON.stringify({
  title: taskForm.title,
  description: taskForm.description,
  dueDate: taskForm.dueDate || null,
  status: taskForm.status || 'To Do',
  listId: selectedListId,
  createdByTutorId: userData.id || 1,
  internsId: taskForm.assignedInternIds || []  // ← CORREGIDO
})
```

#### Línea ~584 (handleUpdateTask):
```javascript
const updateData = {
  title: taskForm.title,
  description: taskForm.description,
  dueDate: taskForm.dueDate || null,
  status: taskForm.status,
  listId: selectedTask.listId,
  createdByTutorId: selectedTask.createdByTutorId,
  internsId: taskForm.assignedInternIds || []  // ← CORREGIDO
};
```

---

## 🎯 Endpoints Afectados

| Endpoint | Método | Campo Corregido | Estado |
|----------|--------|-----------------|--------|
| `/tasks/add` | POST | `internsId` | ✅ Corregido |
| `/tasks/{id}` | PATCH | `internsId` | ✅ Corregido |

---

## 🧪 Testing

Ahora puedes:

1. ✅ **Crear tarea** con internos asignados
2. ✅ **Editar tarea** y cambiar internos asignados
3. ✅ **Ver tarea** con la lista de internos asignados
4. ✅ **Eliminar tarea** (ya funcionaba)

---

## 📝 Notas Adicionales

### Logging Mejorado

Se agregaron logs para debugging:
```javascript
console.log('Updating task with data:', updateData);
console.log('Task ID:', selectedTask.taskId);
console.log('Response status:', response.status);
```

Si hay errores, ahora se mostrará más información:
```javascript
if (!response.ok) {
  const errorText = await response.text();
  console.error('Error response:', errorText);
  throw new Error(`Error al actualizar la tarea: ${response.status} - ${errorText}`);
}
```

---

## ✅ Verificación Backend

El backend procesa correctamente `internsId`:

```java
@PatchMapping("/{id}")
public TaskRequestDTO updateTask(@PathVariable Integer id, @Valid @RequestBody TaskRequestDTO dto) {
    Task existing = this.taskService.getTaskById(id).orElse(null);
    if (existing == null) {
        return null;
    }
    
    Task updated = this.taskService.updateTask(id, dto);
    // Devuelve el task actualizado con los internos asignados
    return new TaskRequestDTO(...);
}
```

---

**Fecha:** Octubre 2024  
**Estado:** ✅ COMPLETADO - Asignación de internos funcional
**Fix:** Cambio de `internIds` → `internsId` para coincidir con DTO del backend
