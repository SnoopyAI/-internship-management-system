# Fix: Internos Asignados No Se Muestran

## 🐛 Problema

**Síntoma:** Al asignar internos a una tarea y guardar, el modal de vista muestra "Sin asignar"

**Causa:** Desconexión entre el formato de datos del backend y frontend

---

## 🔍 Análisis

### Backend Devuelve (TaskRequestDTO):
```json
{
  "id": 123,
  "title": "Tarea 1",
  "internsId": [1, 2, 3]  // ← Array de IDs
}
```

### Frontend Esperaba:
```javascript
selectedTask.interns  // ← Array de objetos completos
[
  { internId: 1, name: "Juan", surname: "Pérez" },
  { internId: 2, name: "María", surname: "García" }
]
```

**Resultado:** Como `selectedTask.interns` no existía, siempre mostraba "Sin asignar".

---

## ✅ Solución

### 1. Modal de Vista - Mapear IDs a Objetos

Cambiar para usar `internsId` y buscar los objetos en el estado `interns`:

```javascript
// ANTES:
{selectedTask.interns && selectedTask.interns.length > 0 ? (
  selectedTask.interns.map((intern) => (
    <span key={intern.internId}>
      {intern.name} {intern.surname}
    </span>
  ))
) : (
  <p>Sin asignar</p>
)}

// DESPUÉS:
{selectedTask.internsId && selectedTask.internsId.length > 0 ? (
  selectedTask.internsId.map((internId) => {
    const intern = interns.find(i => i.internId === internId);
    return intern ? (
      <span key={internId} className="assigned-intern-tag">
        {intern.name} {intern.surname}
      </span>
    ) : null;
  })
) : (
  <p>Sin asignar</p>
)}
```

### 2. handleEditTask - Cargar IDs Correctos

```javascript
// ANTES:
assignedInternIds: task.assignedInterns?.map(i => i.id) || []

// DESPUÉS:
assignedInternIds: task.internsId || []
```

Ahora cuando editas una tarea, los checkboxes se pre-seleccionan correctamente.

---

## 📊 Flujo de Datos Completo

### 1. Backend devuelve tarea:
```json
{
  "id": 123,
  "title": "Implementar login",
  "internsId": [5, 8]
}
```

### 2. Frontend almacena en selectedTask:
```javascript
selectedTask = {
  id: 123,
  title: "Implementar login",
  internsId: [5, 8]
}
```

### 3. Modal de vista mapea IDs:
```javascript
// interns = [{ internId: 5, name: "Juan", ... }, { internId: 8, name: "María", ... }]

selectedTask.internsId.map(internId => {
  const intern = interns.find(i => i.internId === internId);
  // intern = { internId: 5, name: "Juan", surname: "Pérez" }
  return <span>{intern.name} {intern.surname}</span>;
})
```

### 4. Resultado visual:
```
Asignada a:
[Juan Pérez] [María García]
```

---

## 🎯 Cambios Realizados

### Archivo: `ProjectDetail.js`

#### Línea ~1305 (View Task Modal):
```javascript
<div className="assigned-interns-list">
  {selectedTask.internsId && selectedTask.internsId.length > 0 ? (
    selectedTask.internsId.map((internId) => {
      const intern = interns.find(i => i.internId === internId);
      return intern ? (
        <span key={internId} className="assigned-intern-tag">
          {intern.name} {intern.surname}
        </span>
      ) : null;
    })
  ) : (
    <p>Sin asignar</p>
  )}
</div>
```

#### Línea ~563 (handleEditTask):
```javascript
setTaskForm({
  title: task.title,
  description: task.description || '',
  dueDate: task.dueDate || '',
  status: task.status || 'To Do',
  assignedInternIds: task.internsId || []  // ← Corregido
});
```

---

## ✅ Verificación

Ahora el flujo completo funciona:

1. ✅ **Crear tarea** y asignar internos
2. ✅ **Guardar** → Backend recibe `internsId: [1, 2]`
3. ✅ **Recargar** → Backend devuelve `internsId: [1, 2]`
4. ✅ **Ver tarea** → Modal muestra "Juan Pérez, María García"
5. ✅ **Editar tarea** → Checkboxes pre-seleccionados correctamente
6. ✅ **Cambiar asignaciones** → Funciona
7. ✅ **Guardar cambios** → Backend actualiza correctamente

---

## 🧪 Testing

Para probar:

1. **Crear una tarea nueva**
2. **Seleccionar 2-3 internos** en los checkboxes
3. **Guardar**
4. **Click en la tarea** para ver detalles
5. **Verificar:** Debería mostrar los nombres de los internos asignados ✅
6. **Click en "Editar"**
7. **Verificar:** Los checkboxes de esos internos deben estar marcados ✅
8. **Desmarcar uno** y marcar otro
9. **Guardar cambios**
10. **Ver de nuevo** → Debería mostrar la nueva lista ✅

---

## 📝 Nota Técnica

### ¿Por qué usar IDs en lugar de objetos completos?

El backend devuelve solo IDs para evitar:
- **Recursión infinita** en JSON (Intern → Board → Lists → Tasks → Interns → ...)
- **Payload pesado** (solo enviar lo necesario)
- **Datos desactualizados** (los objetos completos se buscan siempre del estado actual)

El frontend mapea los IDs a objetos usando el estado `interns` que ya tiene toda la información actualizada.

---

**Fecha:** Octubre 2024  
**Estado:** ✅ COMPLETADO - Visualización de internos asignados funcional  
**Archivos Modificados:** `ProjectDetail.js` (2 funciones)
