import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Settings.css';

function Settings() {
  const navigate = useNavigate();
  const [activeSection, setActiveSection] = useState('profile');
  const [formData, setFormData] = useState({
    name: localStorage.getItem('userEmail') || '',
    email: localStorage.getItem('userEmail') || '',
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  });

  const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

  const handleSaveProfile = (e) => {
    e.preventDefault();
    alert('Perfil actualizado (funcionalidad pendiente)');
  };

  const handleChangePassword = (e) => {
    e.preventDefault();
    if (formData.newPassword !== formData.confirmPassword) {
      alert('Las contraseñas no coinciden');
      return;
    }
    alert('Contraseña actualizada (funcionalidad pendiente)');
  };

  return (
    <div className="settings-container">
      {/* Header */}
      <header className="top-header">
        <div className="header-brand">
          <h2>SGP</h2>
          <span className="brand-subtitle">Sistema de Gestión</span>
        </div>
        
        <nav className="top-nav">
          <button onClick={() => navigate('/dashboard')} className="nav-item">Inicio</button>
          <button onClick={() => navigate('/participants')} className="nav-item">Equipo</button>
          <button onClick={() => navigate('/tasks')} className="nav-item">Tareas</button>
          <button onClick={() => navigate('/universities')} className="nav-item">Universidades</button>
          <button onClick={() => navigate('/reports')} className="nav-item">Reportes</button>
          <button onClick={() => navigate('/settings')} className="nav-item active">Configuración</button>
        </nav>

        <div className="header-actions">
          <div className="user-info">
            <span className="user-name">{localStorage.getItem('userEmail')}</span>
          </div>
          <button onClick={handleLogout} className="logout-btn">Cerrar Sesión</button>
        </div>
      </header>

      {/* Main Content */}
      <main className="settings-main">
        <div className="page-header">
          <div>
            <h1>⚙️ Configuración</h1>
            <p className="page-subtitle">Administra tu cuenta y preferencias</p>
          </div>
        </div>

        <div className="settings-layout">
          {/* Sidebar */}
          <aside className="settings-sidebar">
            <button
              className={`settings-nav-btn ${activeSection === 'profile' ? 'active' : ''}`}
              onClick={() => setActiveSection('profile')}
            >
              👤 Perfil
            </button>
            <button
              className={`settings-nav-btn ${activeSection === 'security' ? 'active' : ''}`}
              onClick={() => setActiveSection('security')}
            >
              🔒 Seguridad
            </button>
            <button
              className={`settings-nav-btn ${activeSection === 'notifications' ? 'active' : ''}`}
              onClick={() => setActiveSection('notifications')}
            >
              🔔 Notificaciones
            </button>
            <button
              className={`settings-nav-btn ${activeSection === 'appearance' ? 'active' : ''}`}
              onClick={() => setActiveSection('appearance')}
            >
              🎨 Apariencia
            </button>
          </aside>

          {/* Content Area */}
          <div className="settings-content">
            {/* Profile Section */}
            {activeSection === 'profile' && (
              <div className="settings-section">
                <h2>Información del Perfil</h2>
                <form onSubmit={handleSaveProfile} className="settings-form">
                  <div className="form-group">
                    <label>Nombre Completo</label>
                    <input
                      type="text"
                      value={formData.name}
                      onChange={(e) => setFormData({...formData, name: e.target.value})}
                      className="form-input"
                    />
                  </div>
                  <div className="form-group">
                    <label>Correo Electrónico</label>
                    <input
                      type="email"
                      value={formData.email}
                      onChange={(e) => setFormData({...formData, email: e.target.value})}
                      className="form-input"
                    />
                  </div>
                  <button type="submit" className="btn-save">
                    Guardar Cambios
                  </button>
                </form>
              </div>
            )}

            {/* Security Section */}
            {activeSection === 'security' && (
              <div className="settings-section">
                <h2>Seguridad y Contraseña</h2>
                <form onSubmit={handleChangePassword} className="settings-form">
                  <div className="form-group">
                    <label>Contraseña Actual</label>
                    <input
                      type="password"
                      value={formData.currentPassword}
                      onChange={(e) => setFormData({...formData, currentPassword: e.target.value})}
                      className="form-input"
                    />
                  </div>
                  <div className="form-group">
                    <label>Nueva Contraseña</label>
                    <input
                      type="password"
                      value={formData.newPassword}
                      onChange={(e) => setFormData({...formData, newPassword: e.target.value})}
                      className="form-input"
                    />
                  </div>
                  <div className="form-group">
                    <label>Confirmar Nueva Contraseña</label>
                    <input
                      type="password"
                      value={formData.confirmPassword}
                      onChange={(e) => setFormData({...formData, confirmPassword: e.target.value})}
                      className="form-input"
                    />
                  </div>
                  <button type="submit" className="btn-save">
                    Cambiar Contraseña
                  </button>
                </form>
              </div>
            )}

            {/* Notifications Section */}
            {activeSection === 'notifications' && (
              <div className="settings-section">
                <h2>Preferencias de Notificaciones</h2>
                <div className="settings-options">
                  <div className="option-item">
                    <div>
                      <h4>Notificaciones por Email</h4>
                      <p>Recibe actualizaciones importantes por correo</p>
                    </div>
                    <label className="switch">
                      <input type="checkbox" defaultChecked />
                      <span className="slider"></span>
                    </label>
                  </div>
                  <div className="option-item">
                    <div>
                      <h4>Recordatorios de Tareas</h4>
                      <p>Notificaciones sobre tareas próximas a vencer</p>
                    </div>
                    <label className="switch">
                      <input type="checkbox" defaultChecked />
                      <span className="slider"></span>
                    </label>
                  </div>
                  <div className="option-item">
                    <div>
                      <h4>Actualizaciones del Sistema</h4>
                      <p>Novedades y mejoras del sistema</p>
                    </div>
                    <label className="switch">
                      <input type="checkbox" />
                      <span className="slider"></span>
                    </label>
                  </div>
                </div>
              </div>
            )}

            {/* Appearance Section */}
            {activeSection === 'appearance' && (
              <div className="settings-section">
                <h2>Apariencia</h2>
                <div className="settings-options">
                  <div className="option-item">
                    <div>
                      <h4>Tema Oscuro</h4>
                      <p>Activar modo oscuro (ya activo por defecto)</p>
                    </div>
                    <label className="switch">
                      <input type="checkbox" defaultChecked disabled />
                      <span className="slider"></span>
                    </label>
                  </div>
                  <div className="option-item">
                    <div>
                      <h4>Idioma</h4>
                      <p>Selecciona el idioma de la interfaz</p>
                    </div>
                    <select className="form-input" defaultValue="es">
                      <option value="es">Español</option>
                      <option value="en">English</option>
                    </select>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </main>
    </div>
  );
}

export default Settings;
