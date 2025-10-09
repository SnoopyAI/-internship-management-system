import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './ProjectDetail.css';

function ProjectDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [project, setProject] = useState(null);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('overview');
  
  // Estados para tutores
  const [academicTutors, setAcademicTutors] = useState([]);
  const [companyTutor, setCompanyTutor] = useState(null);
  const [showAddTutorModal, setShowAddTutorModal] = useState(false);
  const [tutorType, setTutorType] = useState('academic');
  
  // Estados para listas y tareas
  const [lists, setLists] = useState([]);
  const [showAddListModal, setShowAddListModal] = useState(false);
  const [newListName, setNewListName] = useState('');
  const [showAddTaskModal, setShowAddTaskModal] = useState(false);
  const [selectedListId, setSelectedListId] = useState(null);
  const [newTaskName, setNewTaskName] = useState('');

  useEffect(() => {
    loadProjectDetails();
  }, [id]);

  const loadProjectDetails = async () => {
    const credentials = localStorage.getItem('authCredentials');
    if (!credentials) {
      navigate('/');
      return;
    }

    try {
      // Cargar detalles del proyecto
      const response = await fetch(`http://localhost:8080/boards/${id}`, {
        headers: {
          'Authorization': `Basic ${credentials}`
        }
      });

      if (response.ok) {
        const data = await response.json();
        setProject(data);
        
        // Cargar tutores asociados si existen
        if (data.academyTutors) {
          setAcademicTutors(data.academyTutors);
        }
        if (data.companyTutor) {
          setCompanyTutor(data.companyTutor);
        }
        
        // Cargar listas asociadas si existen
        if (data.lists) {
          setLists(data.lists);
        }
      } else if (response.status === 401) {
        navigate('/');
      }
    } catch (error) {
      console.error('Error loading project:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

  const handleBackToDashboard = () => {
    navigate('/dashboard');
  };

  const handleAddList = async () => {
    if (!newListName.trim()) return;

    const credentials = localStorage.getItem('authCredentials');
    
    try {
      const response = await fetch(`http://localhost:8080/lists/add`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify({
          name: newListName,
          boardId: id
        })
      });

      if (response.ok) {
        setNewListName('');
        setShowAddListModal(false);
        loadProjectDetails(); // Recargar para actualizar las listas
      }
    } catch (error) {
      console.error('Error creating list:', error);
    }
  };

  const handleAddTask = async () => {
    if (!newTaskName.trim() || !selectedListId) return;

    const credentials = localStorage.getItem('authCredentials');
    
    try {
      const response = await fetch(`http://localhost:8080/tasks/add`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify({
          name: newTaskName,
          listId: selectedListId
        })
      });

      if (response.ok) {
        setNewTaskName('');
        setShowAddTaskModal(false);
        setSelectedListId(null);
        loadProjectDetails(); // Recargar para actualizar las tareas
      }
    } catch (error) {
      console.error('Error creating task:', error);
    }
  };

  if (loading) {
    return (
      <div className="project-detail-container">
        <div className="loading">Cargando proyecto...</div>
      </div>
    );
  }

  if (!project) {
    return (
      <div className="project-detail-container">
        <div className="error">Proyecto no encontrado</div>
      </div>
    );
  }

  return (
    <div className="project-detail-container">
      {/* Header Navigation - Same as Dashboard */}
      <header className="top-header">
        <div className="header-brand">
          <h2>SGP</h2>
          <span className="brand-subtitle">Sistema de Gestión</span>
        </div>
        
        <nav className="top-nav">
          <a href="#home" className="nav-item active">Inicio</a>
          <a href="#boards" className="nav-item">Tableros</a>
          <a href="#tasks" className="nav-item">Tareas</a>
          <a href="#team" className="nav-item">Equipo</a>
          <a href="#reports" className="nav-item">Reportes</a>
          <a href="#settings" className="nav-item">Configuración</a>
        </nav>

        <div className="header-actions">
          <div className="user-info">
            <span className="user-name">{localStorage.getItem('userEmail')}</span>
          </div>
          <button onClick={handleLogout} className="logout-btn">Cerrar Sesión</button>
        </div>
      </header>

      {/* Project Title Bar */}
      <div className="project-title-bar">
        <button className="btn-back" onClick={handleBackToDashboard}>
          ← Volver
        </button>
        <h1 className="project-title">{project.name}</h1>
        <div style={{ width: '100px' }}></div> {/* Spacer for alignment */}
      </div>

      {/* Tabs Navigation */}
      <div className="tabs-container">
        <button 
          className={`tab ${activeTab === 'overview' ? 'active' : ''}`}
          onClick={() => setActiveTab('overview')}
        >
          Información General
        </button>
        <button 
          className={`tab ${activeTab === 'tutors' ? 'active' : ''}`}
          onClick={() => setActiveTab('tutors')}
        >
          Tutores
        </button>
        <button 
          className={`tab ${activeTab === 'tasks' ? 'active' : ''}`}
          onClick={() => setActiveTab('tasks')}
        >
          Listas y Tareas
        </button>
      </div>

      {/* Content Area */}
      <div className="project-content">
        {/* Overview Tab */}
        {activeTab === 'overview' && (
          <div className="tab-content">
            <div className="info-card">
              <h2>Descripción del Proyecto</h2>
              <p>{project.description || 'Sin descripción'}</p>
            </div>
            <div className="info-grid">
              <div className="info-card">
                <h3>Fecha de Inicio</h3>
                <p>{project.startDate || 'No definida'}</p>
              </div>
              <div className="info-card">
                <h3>Fecha de Fin</h3>
                <p>{project.endDate || 'No definida'}</p>
              </div>
            </div>
          </div>
        )}

        {/* Tutors Tab */}
        {activeTab === 'tutors' && (
          <div className="tab-content">
            <div className="section">
              <div className="section-header">
                <h2>Tutores Académicos</h2>
                <button 
                  className="btn-add-small"
                  onClick={() => {
                    setTutorType('academic');
                    setShowAddTutorModal(true);
                  }}
                >
                  + Agregar Tutor Académico
                </button>
              </div>
              <div className="tutors-grid">
                {academicTutors.length > 0 ? (
                  academicTutors.map((tutor) => (
                    <div key={tutor.id} className="tutor-card">
                      <h4>{tutor.name}</h4>
                      <p>{tutor.email}</p>
                    </div>
                  ))
                ) : (
                  <p className="empty-message">No hay tutores académicos asignados</p>
                )}
              </div>
            </div>

            <div className="section">
              <div className="section-header">
                <h2>Tutor de Empresa</h2>
                {!companyTutor && (
                  <button 
                    className="btn-add-small"
                    onClick={() => {
                      setTutorType('company');
                      setShowAddTutorModal(true);
                    }}
                  >
                    + Asignar Tutor de Empresa
                  </button>
                )}
              </div>
              {companyTutor ? (
                <div className="tutor-card">
                  <h4>{companyTutor.name}</h4>
                  <p>{companyTutor.email}</p>
                </div>
              ) : (
                <p className="empty-message">No hay tutor de empresa asignado</p>
              )}
            </div>
          </div>
        )}

        {/* Tasks Tab */}
        {activeTab === 'tasks' && (
          <div className="tab-content">
            <div className="section-header">
              <h2>Tablero de Tareas</h2>
              <button 
                className="btn-add-small"
                onClick={() => setShowAddListModal(true)}
              >
                + Nueva Lista
              </button>
            </div>
            
            <div className="lists-container">
              {lists.length > 0 ? (
                lists.map((list) => (
                  <div key={list.id} className="list-card">
                    <div className="list-header">
                      <h3>{list.name}</h3>
                      <button 
                        className="btn-add-task"
                        onClick={() => {
                          setSelectedListId(list.id);
                          setShowAddTaskModal(true);
                        }}
                      >
                        +
                      </button>
                    </div>
                    <div className="tasks-list">
                      {list.tasks && list.tasks.length > 0 ? (
                        list.tasks.map((task) => (
                          <div key={task.id} className="task-item">
                            <div className="task-item-header">
                              <input type="checkbox" />
                              <span>{task.name}</span>
                            </div>
                          </div>
                        ))
                      ) : (
                        <p className="empty-message-small">Arrastra tareas aquí o agrega una nueva</p>
                      )}
                    </div>
                  </div>
                ))
              ) : (
                <p className="empty-message">No hay listas creadas. Crea una lista para comenzar.</p>
              )}
            </div>
          </div>
        )}
      </div>

      {/* Modal: Add Tutor */}
      {showAddTutorModal && (
        <div className="modal-overlay" onClick={() => setShowAddTutorModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Agregar {tutorType === 'academic' ? 'Tutor Académico' : 'Tutor de Empresa'}</h2>
            <p className="modal-info">Esta funcionalidad conectará con los endpoints de tutores del backend</p>
            <div className="modal-actions">
              <button className="btn-cancel" onClick={() => setShowAddTutorModal(false)}>
                Cancelar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Modal: Add List */}
      {showAddListModal && (
        <div className="modal-overlay" onClick={() => setShowAddListModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Nueva Lista</h2>
            <input
              type="text"
              placeholder="Nombre de la lista"
              value={newListName}
              onChange={(e) => setNewListName(e.target.value)}
              className="modal-input"
            />
            <div className="modal-actions">
              <button className="btn-cancel" onClick={() => setShowAddListModal(false)}>
                Cancelar
              </button>
              <button className="btn-submit" onClick={handleAddList}>
                Crear Lista
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Modal: Add Task */}
      {showAddTaskModal && (
        <div className="modal-overlay" onClick={() => setShowAddTaskModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Nueva Tarea</h2>
            <input
              type="text"
              placeholder="Nombre de la tarea"
              value={newTaskName}
              onChange={(e) => setNewTaskName(e.target.value)}
              className="modal-input"
            />
            <div className="modal-actions">
              <button className="btn-cancel" onClick={() => setShowAddTaskModal(false)}>
                Cancelar
              </button>
              <button className="btn-submit" onClick={handleAddTask}>
                Crear Tarea
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default ProjectDetail;
