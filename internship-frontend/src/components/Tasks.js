import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Tasks.css';

function Tasks() {
  const navigate = useNavigate();
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState('all'); // all, pending, progress, completed
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadTasks();
  }, []);

  const loadTasks = async () => {
    // Por ahora, tareas de ejemplo
    // TODO: Conectar con el endpoint real cuando esté disponible
    setTasks([
      { id: 1, name: 'Implementar login', status: 'completed', priority: 'high', dueDate: '2025-10-05', project: 'Sistema de Gestión' },
      { id: 2, name: 'Diseñar dashboard', status: 'progress', priority: 'high', dueDate: '2025-10-12', project: 'Sistema de Gestión' },
      { id: 3, name: 'Revisar código', status: 'pending', priority: 'medium', dueDate: '2025-10-15', project: 'Sistema de Gestión' },
      { id: 4, name: 'Documentar API', status: 'pending', priority: 'low', dueDate: '2025-10-20', project: 'Sistema de Gestión' },
      { id: 5, name: 'Testing unitario', status: 'progress', priority: 'high', dueDate: '2025-10-18', project: 'Backend Services' },
    ]);
    setLoading(false);
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

  const getFilteredTasks = () => {
    let filtered = tasks;

    if (filter === 'pending') {
      filtered = tasks.filter(t => t.status === 'pending');
    } else if (filter === 'progress') {
      filtered = tasks.filter(t => t.status === 'progress');
    } else if (filter === 'completed') {
      filtered = tasks.filter(t => t.status === 'completed');
    }

    return filtered.filter(t =>
      t.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      t.project.toLowerCase().includes(searchTerm.toLowerCase())
    );
  };

  const getStatusLabel = (status) => {
    switch(status) {
      case 'completed': return 'Completada';
      case 'progress': return 'En Progreso';
      case 'pending': return 'Pendiente';
      default: return status;
    }
  };

  const getPriorityLabel = (priority) => {
    switch(priority) {
      case 'high': return '🔴 Alta';
      case 'medium': return '🟡 Media';
      case 'low': return '🟢 Baja';
      default: return priority;
    }
  };

  const filteredTasks = getFilteredTasks();

  if (loading) {
    return (
      <div className="loading-screen">
        <div className="spinner"></div>
        <p className="loading-text">Loading</p>
      </div>
    );
  }

  return (
    <div className="tasks-container">
      {/* Header */}
      <header className="top-header">
        <div className="header-brand">
          <h2>SGP</h2>
          <span className="brand-subtitle">Sistema de Gestión</span>
        </div>
        
        <nav className="top-nav">
          <button onClick={() => navigate('/dashboard')} className="nav-item">Inicio</button>
          <button onClick={() => navigate('/participants')} className="nav-item">Equipo</button>
          <button onClick={() => navigate('/tasks')} className="nav-item active">Tareas</button>
          <button onClick={() => navigate('/universities')} className="nav-item">Universidades</button>
          <button onClick={() => navigate('/reports')} className="nav-item">Reportes</button>
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
      <main className="tasks-main">
        <div className="page-header">
          <div>
            <h1>✓ Mis Tareas</h1>
            <p className="page-subtitle">
              {filteredTasks.length} tareas {filter !== 'all' ? `- ${getStatusLabel(filter)}` : ''}
            </p>
          </div>
        </div>

        {/* Filters and Search */}
        <div className="filters-section">
          <div className="filter-buttons">
            <button 
              className={`filter-btn ${filter === 'all' ? 'active' : ''}`}
              onClick={() => setFilter('all')}
            >
              Todas ({tasks.length})
            </button>
            <button 
              className={`filter-btn ${filter === 'pending' ? 'active' : ''}`}
              onClick={() => setFilter('pending')}
            >
              📋 Pendientes ({tasks.filter(t => t.status === 'pending').length})
            </button>
            <button 
              className={`filter-btn ${filter === 'progress' ? 'active' : ''}`}
              onClick={() => setFilter('progress')}
            >
              🔄 En Progreso ({tasks.filter(t => t.status === 'progress').length})
            </button>
            <button 
              className={`filter-btn ${filter === 'completed' ? 'active' : ''}`}
              onClick={() => setFilter('completed')}
            >
              ✅ Completadas ({tasks.filter(t => t.status === 'completed').length})
            </button>
          </div>

          <input
            type="text"
            placeholder="🔍 Buscar tareas..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
        </div>

        {/* Tasks List */}
        <div className="tasks-list">
          {filteredTasks.length > 0 ? (
            filteredTasks.map((task) => (
              <div key={task.id} className={`task-card status-${task.status}`}>
                <div className="task-header">
                  <h3>{task.name}</h3>
                  <span className={`priority-badge ${task.priority}`}>
                    {getPriorityLabel(task.priority)}
                  </span>
                </div>
                <div className="task-meta">
                  <span className="task-project">📁 {task.project}</span>
                  <span className="task-date">📅 {task.dueDate}</span>
                </div>
                <div className="task-footer">
                  <span className={`status-badge status-${task.status}`}>
                    {getStatusLabel(task.status)}
                  </span>
                  <div className="task-actions">
                    <button className="btn-task">Ver Detalles</button>
                  </div>
                </div>
              </div>
            ))
          ) : (
            <div className="empty-state">
              <p>No se encontraron tareas</p>
            </div>
          )}
        </div>
      </main>
    </div>
  );
}

export default Tasks;
