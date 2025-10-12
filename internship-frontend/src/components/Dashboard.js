import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Dashboard.css';

function Dashboard() {
  const navigate = useNavigate();
  const [userEmail, setUserEmail] = useState('');
  const [userRoles, setUserRoles] = useState([]);
  const [boards, setBoards] = useState([]);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [newProjectName, setNewProjectName] = useState('');
  const [loading, setLoading] = useState(false);
  const [pageLoading, setPageLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    // Cargar datos del usuario del localStorage
    const email = localStorage.getItem('userEmail');
    const roles = JSON.parse(localStorage.getItem('userRoles') || '[]');
    const credentials = localStorage.getItem('authCredentials');
    
    // Si no hay credenciales, redirigir al login
    if (!credentials) {
      console.warn('No hay credenciales guardadas. Redirigiendo al login...');
      navigate('/');
      return;
    }
    
    setUserEmail(email || 'Usuario');
    setUserRoles(roles);

    // Cargar proyectos reales del backend
    loadBoards();
    
    // Simular un pequeño delay para mostrar el loading
    setTimeout(() => {
      setPageLoading(false);
    }, 800);
  }, []);

  const loadBoards = async () => {
    try {
      const credentials = localStorage.getItem('authCredentials');
      console.log('Credentials:', credentials ? 'Exist' : 'Missing');
      
      const response = await fetch('http://localhost:8080/boards/ReadAll', {
        headers: {
          'Authorization': `Basic ${credentials}`,
          'Content-Type': 'application/json'
        }
      });
      
      console.log('Response status:', response.status);
      
      if (response.ok) {
        const data = await response.json();
        console.log('Proyectos cargados:', data);
        setBoards(data);
      } else {
        console.error('Error al cargar proyectos - Status:', response.status);
        const errorText = await response.text();
        console.error('Error details:', errorText);
      }
    } catch (err) {
      console.error('Error al conectar con el servidor:', err);
    }
  };

  const handleCreateProject = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    if (!newProjectName.trim()) {
      setError('El nombre del proyecto es requerido');
      setLoading(false);
      return;
    }

    try {
      const credentials = localStorage.getItem('authCredentials');
      const response = await fetch('http://localhost:8080/boards/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify({
          name: newProjectName,
          description: '',
          startDate: null,
          endDate: null,
          academyTutorId: null,
          companyTutorId: null
        })
      });

      if (response.ok) {
        const newBoard = await response.json();
        console.log('Proyecto creado:', newBoard);
        
        // Recargar la lista de proyectos
        await loadBoards();
        
        // Cerrar modal y limpiar formulario
        setShowCreateModal(false);
        setNewProjectName('');
      } else {
        const errorText = await response.text();
        console.error('Error response:', errorText);
        setError('Error al crear el proyecto');
      }
    } catch (err) {
      setError('Error al conectar con el servidor');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    window.location.href = '/';
  };

  const handleDeleteBoard = async (boardId, boardName) => {
    if (!window.confirm(`¿Estás seguro de eliminar el proyecto "${boardName}"? Esta acción no se puede deshacer.`)) {
      return;
    }

    try {
      const credentials = localStorage.getItem('authCredentials');
      const response = await fetch(`http://localhost:8080/boards/${boardId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Basic ${credentials}`
        }
      });

      if (response.ok) {
        await loadBoards();
      } else {
        alert('Error al eliminar el proyecto');
      }
    } catch (error) {
      console.error('Error deleting board:', error);
      alert('Error al eliminar el proyecto');
    }
  };

  const getUserRole = () => {
    if (userRoles.length === 0) return 'Usuario';
    const role = userRoles[0].authority || '';
    if (role.includes('COMPANY')) return 'Tutor de Empresa';
    if (role.includes('ACADEMY')) return 'Tutor Académico';
    if (role.includes('INTERN')) return 'Pasante';
    return 'Usuario';
  };

  const getRandomColor = () => {
    const colors = ['#6366f1', '#8b5cf6', '#ec4899', '#f59e0b', '#10b981', '#3b82f6'];
    return colors[Math.floor(Math.random() * colors.length)];
  };

  // Mostrar pantalla de carga
  if (pageLoading) {
    return (
      <div className="loading-screen">
        <div className="spinner"></div>
        <p className="loading-text">Loading</p>
      </div>
    );
  }

  return (
    <div className="dashboard-container">
      {/* Header Navigation */}
      <header className="top-header">
        <div className="header-brand">
          <h2>SGP</h2>
          <span className="brand-subtitle">Sistema de Gestión</span>
        </div>
        
        <nav className="top-nav">
          <button onClick={() => navigate('/dashboard')} className="nav-item active">Inicio</button>
          <button onClick={() => navigate('/participants')} className="nav-item">Participantes</button>
          <button onClick={() => navigate('/universities')} className="nav-item">Universidades</button>
          <button onClick={() => navigate('/settings')} className="nav-item">Configuración</button>
        </nav>

        <div className="header-actions">
          <div className="user-info">
            <span className="user-name">{userEmail}</span>
            <span className="user-role-badge">{getUserRole()}</span>
          </div>
          <button onClick={handleLogout} className="logout-btn">Cerrar Sesión</button>
        </div>
      </header>

      {/* Main Content */}
      <main className="main-content">
        {/* Boards Section */}
        <section className="boards-section">
          <div className="section-header">
            <h2>Mis Proyectos</h2>
            <button className="btn-add" onClick={() => setShowCreateModal(true)}>+ Nuevo Proyecto</button>
          </div>
          
          <div className="boards-grid">
            {boards.length === 0 ? (
              <div className="empty-state">
                <p>No tienes proyectos aún. ¡Crea tu primer proyecto!</p>
              </div>
            ) : (
              boards.map(board => (
                <div key={board.id} className="board-card" style={{ borderTopColor: getRandomColor() }}>
                  <div className="board-header">
                    <h3>{board.name}</h3>
                  </div>
                  <div className="board-body">
                    <p className="board-description">{board.description || 'Sin descripción'}</p>
                    <div className="board-dates">
                      {board.startDate && (
                        <span className="date-badge">
                          Inicio: {new Date(board.startDate).toLocaleDateString()}
                        </span>
                      )}
                    </div>
                  </div>
                  <div className="board-footer">
                    <button className="btn-view" onClick={() => navigate(`/project/${board.id}`)}>
                      Ver proyecto →
                    </button>
                    <button 
                      className="btn-delete-board" 
                      onClick={(e) => {
                        e.stopPropagation();
                        handleDeleteBoard(board.id, board.name);
                      }}
                      title="Eliminar proyecto"
                    >
                      Eliminar
                    </button>
                  </div>
                </div>
              ))
            )}
          </div>
        </section>

        {/* Modal para crear proyecto */}
        {showCreateModal && (
          <div className="modal-overlay" onClick={() => setShowCreateModal(false)}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
              <div className="modal-header">
                <h2>Crear Nuevo Proyecto</h2>
                <button className="modal-close" onClick={() => setShowCreateModal(false)}>×</button>
              </div>
              
              <form onSubmit={handleCreateProject}>
                <div className="form-group">
                  <label htmlFor="projectName">Nombre del Proyecto:</label>
                  <input
                    type="text"
                    id="projectName"
                    value={newProjectName}
                    onChange={(e) => setNewProjectName(e.target.value)}
                    placeholder="Ej: Proyecto de Pasantías"
                    required
                    autoFocus
                  />
                </div>

                {error && <div className="error-message">{error}</div>}

                <div className="modal-actions">
                  <button type="button" className="btn-cancel" onClick={() => setShowCreateModal(false)}>
                    Cancelar
                  </button>
                  <button type="submit" className="btn-submit" disabled={loading}>
                    {loading ? 'Creando...' : 'Crear Proyecto'}
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </main>
    </div>
  );
}

export default Dashboard;
