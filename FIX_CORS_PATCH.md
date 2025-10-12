# Fix DEFINITIVO: Failed to Fetch - CORS bloqueando PATCH

## 🐛 Problema

**Error:** "Failed to fetch" al actualizar tareas

**Síntoma:** El navegador no podía hacer peticiones PATCH al backend

---

## 🔍 Causa Raíz

La configuración de **CORS** en el backend no incluía el método **PATCH** en los métodos permitidos.

### Archivo: `CorsConfig.java`

**ANTES:**
```java
configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
```
❌ No incluye PATCH

**DESPUÉS:**
```java
configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
```
✅ Incluye PATCH

---

## ✅ Solución

### 1. Modificar CorsConfig.java

Agregar **"PATCH"** a la lista de métodos permitidos:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

### 2. Reiniciar el Backend

**IMPORTANTE:** Debes reiniciar el servidor Spring Boot para que tome la nueva configuración.

#### En el terminal del backend:
1. **Detener** el servidor (Ctrl+C)
2. **Ejecutar** de nuevo:
   ```bash
   cd bootcamp_proyect
   ./mvnw spring-boot:run
   ```

---

## 📊 Timeline de Fixes

### Fix 1: Autenticación ✅
- Cambio de `Bearer token` → `Basic authCredentials`
- **Resultado:** Autenticación funcionando

### Fix 2: Nombre de Campo ✅
- Cambio de `internIds` → `internsId`
- **Resultado:** Campo mapeado correctamente

### Fix 3: CORS ✅ (ACTUAL)
- Agregado **PATCH** a métodos permitidos
- **Resultado:** Peticiones PATCH permitidas

---

## 🧪 Pasos para Probar

1. **Reiniciar el backend**
   ```bash
   cd bootcamp_proyect
   ./mvnw spring-boot:run
   ```

2. **Refrescar el frontend** (F5 en el navegador)

3. **Intentar editar una tarea:**
   - Click en una tarea → Se abre modal de vista
   - Click en "Editar"
   - Modificar título o asignar internos
   - Click en "Guardar Cambios"
   - ✅ Debería funcionar sin errores

---

## 🔍 Cómo Detectar Errores CORS

En la consola del navegador (F12), los errores CORS se ven así:

```
Access to fetch at 'http://localhost:8080/tasks/123' from origin 'http://localhost:3000' 
has been blocked by CORS policy: Method PATCH is not allowed by Access-Control-Allow-Methods.
```

---

## ✅ Verificación

Después de reiniciar el backend, verifica en la consola del navegador:

1. **Abrir DevTools** (F12)
2. **Ir a pestaña Network**
3. **Editar una tarea**
4. **Buscar la petición PATCH** a `/tasks/{id}`
5. **Verificar:**
   - Status: `200 OK` ✅
   - Headers: `Access-Control-Allow-Methods: GET, POST, PUT, PATCH, DELETE, OPTIONS` ✅

---

## 📝 Resumen de Cambios en el Backend

### Archivo Modificado:
`bootcamp_proyect/src/main/java/com/informationconfig/spring/bootcamp/bootcamp_proyect/security/CorsConfig.java`

### Línea Modificada:
```java
// Línea 17
configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
```

---

## ⚠️ IMPORTANTE

**Debes reiniciar el backend** para que este cambio tenga efecto. Los cambios en archivos de configuración de Spring Boot requieren reinicio completo.

---

**Fecha:** Octubre 2024  
**Estado:** ✅ COMPLETADO - CORS configurado correctamente  
**Acción Requerida:** 🔴 REINICIAR BACKEND
