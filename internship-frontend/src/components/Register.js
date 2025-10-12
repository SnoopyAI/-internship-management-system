import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';

function Register() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: 'INTERN' // Por defecto
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    // Validaciones
    if (!formData.name || !formData.email || !formData.password) {
      setError('Todos los campos son obligatorios');
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      setError('Las contraseñas no coinciden');
      return;
    }

    if (formData.password.length < 6) {
      setError('La contraseña debe tener al menos 6 caracteres');
      return;
    }

    setLoading(true);

    try {
      let endpoint = '';
      let body = {};

      // Según el rol, usar endpoint correspondiente
      if (formData.role === 'INTERN') {
        endpoint = 'http://localhost:8080/interns/add';
        body = {
          name: formData.name,
          email: formData.email,
          password: formData.password,
          career: '',
          semester: null,
          academyTutorId: null,
          universityId: null
        };
      } else if (formData.role === 'ACADEMIC') {
        endpoint = 'http://localhost:8080/academyTutors/add';
        body = {
          name: formData.name,
          email: formData.email,
          password: formData.password,
          department: '',
          universityId: null
        };
      } else if (formData.role === 'COMPANY') {
        endpoint = 'http://localhost:8080/companyTutors/add';
        body = {
          name: formData.name,
          email: formData.email,
          password: formData.password
        };
      }

      const response = await fetch(endpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
      });

      if (response.ok) {
        alert('¡Registro exitoso! Ahora puedes iniciar sesión');
        navigate('/');
      } else {
        const errorData = await response.text();
        setError(errorData || 'Error al registrarse. El email podría estar en uso.');
      }
    } catch (error) {
      console.error('Error:', error);
      setError('Error de conexión. Intenta de nuevo.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-container">
      <div className="register-card">
        <div className="register-header">
          <h1>Crear Cuenta</h1>
          <p>Regístrate para acceder al Sistema de Gestión de Pasantías</p>
        </div>

        <form onSubmit={handleSubmit} className="register-form">
          {error && <div className="error-message">{error}</div>}

          <div className="form-group">
            <label>Nombre Completo</label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              placeholder="Juan Pérez"
              className="form-input"
            />
          </div>

          <div className="form-group">
            <label>Correo Electrónico</label>
            <input
              type="email"
              value={formData.email}
              onChange={(e) => setFormData({ ...formData, email: e.target.value })}
              placeholder="juan@ejemplo.com"
              className="form-input"
            />
          </div>

          <div className="form-group">
            <label>Tipo de Usuario</label>
            <select
              value={formData.role}
              onChange={(e) => setFormData({ ...formData, role: e.target.value })}
              className="form-input"
            >
              <option value="INTERN">Interno (Pasante)</option>
              <option value="ACADEMIC">Tutor Académico</option>
              <option value="COMPANY">Tutor de Empresa</option>
            </select>
          </div>

          <div className="form-group">
            <label>Contraseña</label>
            <input
              type="password"
              value={formData.password}
              onChange={(e) => setFormData({ ...formData, password: e.target.value })}
              placeholder="Mínimo 6 caracteres"
              className="form-input"
            />
          </div>

          <div className="form-group">
            <label>Confirmar Contraseña</label>
            <input
              type="password"
              value={formData.confirmPassword}
              onChange={(e) => setFormData({ ...formData, confirmPassword: e.target.value })}
              placeholder="Repite tu contraseña"
              className="form-input"
            />
          </div>

          <button 
            type="submit" 
            className="btn-register"
            disabled={loading}
          >
            {loading ? 'Registrando...' : 'Crear Cuenta'}
          </button>

          <div className="login-link">
            ¿Ya tienes cuenta? 
            <button 
              type="button"
              onClick={() => navigate('/')}
              className="link-button"
            >
              Iniciar Sesión
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Register;
