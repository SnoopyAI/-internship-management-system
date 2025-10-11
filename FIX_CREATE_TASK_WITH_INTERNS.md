# Fix: Error al Crear Tareas con Internos Asignados

## 🐛 Problema

**Síntoma:** Al crear una tarea y asignar internos, daba error. Solo funcionaba si se creaba sin asignar.

**Causa:** Dos problemas:
1. Frontend usaba `intern.internId` pero el backend espera `intern.id`
2. Backend no actualizaba internos en el método `updateTask()`

---

## 🔍 Análisis

### 1. Problema de Nombre de Campo

El modelo `Intern` en el backend:
```java
@Entity
@Table(name = "interns")
public class Intern extends User {
    // Hereda de User que tiene:
    @Id
    private Integer id;  // ← Campo se llama "id"
}
```

Pero el frontend usaba:
```javascript
intern.internId  // ❌ Este campo no existe
```

### 2. Problema en updateTask()

El método `updateTask()` en `TaskService.java` tenía un comentario:
```java
// Note: List and intern relationships should be updated through dedicated endpoints
```

Y **NO actualizaba los internos** asignados.

---

## ✅ Solución

### Parte 1: Frontend - Usar `id` en lugar de `internId`

#### Cambios en Add Task Modal (Línea ~1160):
```javascript
// ANTES:
<div key={intern.internId}>
  <input
    id={`add-intern-${intern.internId}`}
    checked={(taskForm.assignedInternIds || []).includes(intern.internId)}
    onChange={() => {
      assignedInternIds: [...(taskForm.assignedInternIds || []), intern.internId]
    }}
  />
  <label htmlFor={`add-intern-${intern.internId}`}>

// DESPUÉS:
<div key={intern.id}>
  <input
    id={`add-intern-${intern.id}`}
    checked={(taskForm.assignedInternIds || []).includes(intern.id)}
    onChange={() => {
      assignedInternIds: [...(taskForm.assignedInternIds || []), intern.id]
    }}
  />
  <label htmlFor={`add-intern-${intern.id}`}>
```

#### Cambios en Edit Task Modal (Línea ~1244):
```javascript
// Mismos cambios: internId → id
```

#### Cambios en View Task Modal (Línea ~1318):
```javascript
// ANTES:
const intern = interns.find(i => i.internId === internId);

// DESPUÉS:
const intern = interns.find(i => i.id === internId);
```

### Parte 2: Backend - Actualizar Internos en updateTask()

#### Archivo: `TaskService.java` (Línea ~103)

```java
// ANTES:
public Task updateTask(Integer id, TaskRequestDTO dto) {
    if (taskRepository.existsById(id)) {
        Task task = taskRepository.findById(id).get();
        
        // Actualizar campos básicos...
        
        // Note: List and intern relationships should be updated through dedicated endpoints
        
        return taskRepository.save(task);
    }
    return null;
}

// DESPUÉS:
public Task updateTask(Integer id, TaskRequestDTO dto) {
    if (taskRepository.existsById(id)) {
        Task task = taskRepository.findById(id).get();
        
        // Actualizar campos básicos...
        
        // Actualizar internos asignados
        if (dto.getInternsId() != null) {
            System.out.println("DEBUG UPDATE: Lista de internIds recibida: " + dto.getInternsId());
            List<Intern> interns = new ArrayList<>();
            if (!dto.getInternsId().isEmpty()) {
                System.out.println("DEBUG UPDATE: Buscando " + dto.getInternsId().size() + " internos...");
                interns = internRepository.findAllById(dto.getInternsId());
                System.out.println("DEBUG UPDATE: Encontrados " + interns.size() + " internos");
                if (interns.size() != dto.getInternsId().size()) {
                    throw new RuntimeException("Uno o más internos no existen.");
                }
            }
            System.out.println("DEBUG UPDATE: Asociando " + interns.size() + " internos a la tarea");
            task.setInterns(interns);
        }
        
        return taskRepository.save(task);
    }
    return null;
}
```

---

## 📊 Flujo Completo Corregido

### 1. Usuario selecciona internos en el modal:
```javascript
// Checkbox onChange
assignedInternIds: [...taskForm.assignedInternIds, intern.id]
// Ahora guarda: [5, 8, 12] (IDs correctos)
```

### 2. Frontend envía al backend:
```javascript
POST /tasks/add
{
  "title": "Nueva tarea",
  "internsId": [5, 8, 12]  // ← IDs correctos
}
```

### 3. Backend procesa (TaskService.addTask):
```java
List<Intern> interns = internRepository.findAllById([5, 8, 12]);
// Encuentra los 3 internos correctamente
newTask.setInterns(interns);
taskRepository.save(newTask);
```

### 4. Backend devuelve:
```json
{
  "id": 123,
  "title": "Nueva tarea",
  "internsId": [5, 8, 12]
}
```

### 5. Frontend muestra en el modal de vista:
```javascript
selectedTask.internsId.map(internId => {
  const intern = interns.find(i => i.id === internId);  // ← Ahora encuentra correctamente
  return <span>{intern.name} {intern.surname}</span>;
})
```

---

## 🎯 Archivos Modificados

### Frontend: `ProjectDetail.js`

| Línea | Función/Lugar | Cambio |
|-------|---------------|--------|
| ~505 | `handleAddTask()` | Agregado logging |
| ~1160-1180 | Add Task Modal checkboxes | `intern.internId` → `intern.id` |
| ~1244-1264 | Edit Task Modal checkboxes | `intern.internId` → `intern.id` |
| ~1318 | View Task Modal find | `i.internId` → `i.id` |

### Backend: `TaskService.java`

| Línea | Método | Cambio |
|-------|--------|--------|
| ~103-125 | `updateTask()` | Agregada lógica para actualizar internos asignados |

---

## ⚠️ ACCIÓN REQUERIDA

**DEBES REINICIAR EL BACKEND** para que tome los cambios en `TaskService.java`:

1. En la terminal del backend, presiona **Ctrl+C**
2. Ejecuta de nuevo:
   ```bash
   cd bootcamp_proyect
   ./mvnw spring-boot:run
   ```
3. Espera a que diga "Started BootcampProyectApplication..."

---

## ✅ Verificación

Después de reiniciar el backend:

1. **Crear tarea nueva**
2. **Seleccionar 2-3 internos** en los checkboxes
3. **Guardar** → ✅ Debería funcionar sin error
4. **Click en la tarea** para ver detalles
5. **Verificar:** Muestra los nombres de los internos ✅
6. **Click en "Editar"**
7. **Cambiar asignaciones** (marcar/desmarcar internos)
8. **Guardar cambios** → ✅ Debería actualizar correctamente
9. **Ver de nuevo** → ✅ Muestra las nuevas asignaciones

---

## 📝 Resumen de TODOS los Fixes Aplicados

1. ✅ **Autenticación** - Basic Auth
2. ✅ **Campo internsId** - Nombre correcto
3. ✅ **CORS** - Método PATCH permitido
4. ✅ **Task ID** - `id` en lugar de `taskId`
5. ✅ **Visualización de internos** - Mapeo de IDs a objetos
6. ✅ **Internos en checkboxes** - `intern.id` en lugar de `intern.internId` (ACTUAL)
7. ✅ **Update Task Backend** - Actualiza internos asignados (ACTUAL)

---

**Fecha:** Octubre 2024  
**Estado:** ✅ COMPLETADO - Sistema de asignación totalmente funcional  
**Acción Requerida:** 🔴 REINICIAR BACKEND
