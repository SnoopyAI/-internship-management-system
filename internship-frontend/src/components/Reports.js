import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Reports.css';

function Reports() {
  const navigate = useNavigate();
  const [timeRange, setTimeRange] = useState('month'); // week, month, year

  const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

  // Datos de ejemplo para los reportes
  const stats = {
    totalProjects: 5,
    activeProjects: 3,
    completedTasks: 24,
    pendingTasks: 12,
    totalParticipants: 15,
    averageProgress: 68
  };

  return (
    <div className="reports-container">
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
          <button onClick={() => navigate('/reports')} className="nav-item active">Reportes</button>
          <button onClick={() => navigate('/settings')} className="nav-item">Configuración</button>
        </nav>

        <div className="header-actions">
          <div className="user-info">
            <span className="user-name">{localStorage.getItem('userEmail')}</span>
          </div>
          <button onClick={handleLogout} className="logout-btn">Cerrar Sesión</button>
        </div>
      </header>

      {/* Main Content */}
      <main className="reports-main">
        <div className="page-header">
          <div>
            <h1>📊 Reportes y Estadísticas</h1>
            <p className="page-subtitle">Visualiza el progreso y rendimiento del sistema</p>
          </div>
          <div className="time-range-selector">
            <button 
              className={`range-btn ${timeRange === 'week' ? 'active' : ''}`}
              onClick={() => setTimeRange('week')}
            >
              Semana
            </button>
            <button 
              className={`range-btn ${timeRange === 'month' ? 'active' : ''}`}
              onClick={() => setTimeRange('month')}
            >
              Mes
            </button>
            <button 
              className={`range-btn ${timeRange === 'year' ? 'active' : ''}`}
              onClick={() => setTimeRange('year')}
            >
              Año
            </button>
          </div>
        </div>

        {/* Stats Grid */}
        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-icon">📁</div>
            <div className="stat-info">
              <h3>{stats.totalProjects}</h3>
              <p>Proyectos Totales</p>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon">🚀</div>
            <div className="stat-info">
              <h3>{stats.activeProjects}</h3>
              <p>Proyectos Activos</p>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon">✅</div>
            <div className="stat-info">
              <h3>{stats.completedTasks}</h3>
              <p>Tareas Completadas</p>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-info">
              <h3>{stats.pendingTasks}</h3>
              <p>Tareas Pendientes</p>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-info">
              <h3>{stats.totalParticipants}</h3>
              <p>Participantes</p>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-info">
              <h3>{stats.averageProgress}%</h3>
              <p>Progreso Promedio</p>
            </div>
          </div>
        </div>

        {/* Charts Section */}
        <div className="charts-section">
          <div className="chart-card">
            <h2>📊 Rendimiento por Proyecto</h2>
            <div className="chart-placeholder">
              <p>Gráfico de barras - Próximamente</p>
            </div>
          </div>

          <div className="chart-card">
            <h2>📈 Progreso en el Tiempo</h2>
            <div className="chart-placeholder">
              <p>Gráfico de líneas - Próximamente</p>
            </div>
          </div>

          <div className="chart-card">
            <h2>🎯 Distribución de Tareas</h2>
            <div className="chart-placeholder">
              <p>Gráfico circular - Próximamente</p>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}

export default Reports;
