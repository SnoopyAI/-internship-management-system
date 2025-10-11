import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      console.log('=== DEBUG LOGIN ===');
      console.log('Email:', email);
      console.log('URL:', 'http://localhost:8080/auth/login');
      
      // Hacer petición al backend (nuevo endpoint de login)
      const response = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: email,
          password: password
        })
      });

      console.log('Response status:', response.status);
      console.log('Response ok:', response.ok);

      if (response.ok) {
        // Login exitoso
        const data = await response.json();
        console.log('Login exitoso:', data);
        
        // Guardar información en localStorage (para mantener sesión)
        localStorage.setItem('userEmail', data.email);
        localStorage.setItem('userRoles', JSON.stringify(data.roles));
        
        // Guardar credenciales codificadas para autenticación básica
        const credentials = btoa(`${email}:${password}`);
        localStorage.setItem('authCredentials', credentials);
        console.log('Credenciales guardadas:', credentials);
        
        // Redirigir al dashboard
        navigate('/dashboard');
      } else {
        const errorData = await response.json();
        console.log('Error response:', errorData);
        setError(errorData.message || 'Email o contraseña incorrectos');
      }
    } catch (err) {
      setError('Error al conectar con el servidor');
      console.error('Error completo:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Sistema de Gestión de Pasantías</h2>
        <h3>Iniciar Sesión</h3>
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">Email:</label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="usuario@empresa.com"
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Contraseña:</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              placeholder="Ingresa tu contraseña"
            />
          </div>

          {error && <div className="error-message">{error}</div>}

          <button type="submit" disabled={loading}>
            {loading ? 'Ingresando...' : 'Ingresar'}
          </button>

          <div className="register-link">
            ¿No tienes cuenta? 
            <button 
              type="button"
              onClick={() => navigate('/register')}
              className="link-button"
            >
              Regístrate aquí
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Login;
