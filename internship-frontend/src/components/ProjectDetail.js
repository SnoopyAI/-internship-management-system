import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './ProjectDetail.css';

function ProjectDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [project, setProject] = useState(null);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('overview');
  
  // Estados para tutores e internos
  const [academicTutors, setAcademicTutors] = useState([]);
  const [companyTutor, setCompanyTutor] = useState(null);
  const [interns, setInterns] = useState([]);
  const [showAddParticipantModal, setShowAddParticipantModal] = useState(false);
  const [participantType, setParticipantType] = useState('academic'); // 'academic' o 'intern'
  
  // Estados para formulario de participante
  const [participantForm, setParticipantForm] = useState({
    name: '',
    email: '',
    password: '',
    department: '', // Para tutor académico
    career: '', // Para interno
    semester: '', // Para interno
    universityId: null
  });
  
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
        
        // Cargar internos asociados al proyecto
        const internsResponse = await fetch(`http://localhost:8080/interns/ReadAll`, {
          headers: {
            'Authorization': `Basic ${credentials}`
          }
        });
        
        if (internsResponse.ok) {
          const allInterns = await internsResponse.json();
          // Filtrar internos que pertenecen a este board
          const projectInterns = allInterns.filter(intern => intern.boardId === parseInt(id));
          setInterns(projectInterns);
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

  const handleAddParticipant = async () => {
    const credentials = localStorage.getItem('authCredentials');
    
    try {
      let endpoint = '';
      let body = {};

      if (participantType === 'academic') {
        endpoint = 'http://localhost:8080/academytutors/add';
        body = {
          name: participantForm.name,
          email: participantForm.email,
          password: participantForm.password,
          department: participantForm.department,
          universityId: participantForm.universityId
        };
      } else if (participantType === 'intern') {
        endpoint = 'http://localhost:8080/interns/add';
        body = {
          name: participantForm.name,
          email: participantForm.email,
          password: participantForm.password,
          universityId: participantForm.universityId,
          career: participantForm.career,
          semester: participantForm.semester,
          boardId: id
        };
      }

      const response = await fetch(endpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify(body)
      });

      if (response.ok) {
        // Limpiar formulario
        setParticipantForm({
          name: '',
          email: '',
          password: '',
          department: '',
          career: '',
          semester: '',
          universityId: null
        });
        setShowAddParticipantModal(false);
        loadProjectDetails(); // Recargar para actualizar la lista
      } else {
        const errorData = await response.text();
        alert('Error al agregar participante: ' + errorData);
      }
    } catch (error) {
      console.error('Error adding participant:', error);
      alert('Error al agregar participante');
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

      {/* Main Layout: Sidebar + Content */}
      <div className="project-layout">
        {/* Sidebar */}
        <aside className="project-sidebar">
          <button className="btn-back-sidebar" onClick={handleBackToDashboard}>
            ← Volver al Dashboard
          </button>
          
          <div className="sidebar-section">
            <h2 className="sidebar-project-name">{project.name}</h2>
            <p className="sidebar-description">{project.description || 'Sin descripción'}</p>
          </div>

          <div className="sidebar-section">
            <h3 className="sidebar-heading">📅 Información</h3>
            <div className="sidebar-info-item">
              <span className="info-label">Inicio:</span>
              <span className="info-value">{project.startDate || 'No definida'}</span>
            </div>
            <div className="sidebar-info-item">
              <span className="info-label">Fin:</span>
              <span className="info-value">{project.endDate || 'No definida'}</span>
            </div>
          </div>

          <div className="sidebar-section">
            <h3 className="sidebar-heading">👥 Participantes</h3>
            {academicTutors.length > 0 && (
              <div className="tutor-list-sidebar">
                <p className="tutor-category">Tutores Académicos:</p>
                {academicTutors.map((tutor) => (
                  <div key={tutor.id} className="tutor-item-sidebar">
                    {tutor.name}
                  </div>
                ))}
              </div>
            )}
            {interns.length > 0 && (
              <div className="tutor-list-sidebar">
                <p className="tutor-category">Internos:</p>
                {interns.map((intern) => (
                  <div key={intern.id} className="tutor-item-sidebar">
                    {intern.name}
                  </div>
                ))}
              </div>
            )}
            {companyTutor && (
              <div className="tutor-list-sidebar">
                <p className="tutor-category">Tutor de Empresa:</p>
                <div className="tutor-item-sidebar">{companyTutor.name}</div>
              </div>
            )}
            {academicTutors.length === 0 && !companyTutor && interns.length === 0 && (
              <p className="sidebar-empty">Sin participantes asignados</p>
            )}
          </div>

          <div className="sidebar-section">
            <button 
              className="btn-sidebar-action"
              onClick={() => {
                setParticipantType('academic');
                setShowAddParticipantModal(true);
              }}
            >
              + Agregar Participante
            </button>
          </div>
        </aside>

        {/* Main Content Area */}
        <main className="project-main-content">
          {/* Tabs Navigation */}
          <div className="tabs-container">
            <button 
              className={`tab ${activeTab === 'overview' ? 'active' : ''}`}
              onClick={() => setActiveTab('overview')}
            >
              📋 Información
            </button>
            <button 
              className={`tab ${activeTab === 'tutors' ? 'active' : ''}`}
              onClick={() => setActiveTab('tutors')}
            >
              👥 Participantes
            </button>
            <button 
              className={`tab ${activeTab === 'tasks' ? 'active' : ''}`}
              onClick={() => setActiveTab('tasks')}
            >
              ✓ Tablero de Tareas
            </button>
          </div>

          {/* Tab Content */}
          <div className="tab-content-area">
            {/* Overview Tab */}
            {activeTab === 'overview' && (
              <div className="tab-content">
                <div className="content-card">
                  <h2>📝 Descripción del Proyecto</h2>
                  <p className="project-description-full">{project.description || 'Sin descripción disponible'}</p>
                </div>
              </div>
            )}

            {/* Tutors Tab */}
            {activeTab === 'tutors' && (
              <div className="tab-content">
                <div className="content-section">
                  <div className="section-header">
                    <h2>Tutores Académicos</h2>
                    <button 
                      className="btn-add"
                      onClick={() => {
                        setParticipantType('academic');
                        setShowAddParticipantModal(true);
                      }}
                    >
                      + Agregar Tutor Académico
                    </button>
                  </div>
                  <div className="tutors-grid">
                    {academicTutors.length > 0 ? (
                      academicTutors.map((tutor) => (
                        <div key={tutor.id} className="tutor-card">
                          <div className="tutor-icon">🎓</div>
                          <h4>{tutor.name}</h4>
                          <p>{tutor.email}</p>
                          {tutor.department && <p className="tutor-detail">{tutor.department}</p>}
                        </div>
                      ))
                    ) : (
                      <p className="empty-message">No hay tutores académicos asignados</p>
                    )}
                  </div>
                </div>

                <div className="content-section">
                  <div className="section-header">
                    <h2>Internos</h2>
                    <button 
                      className="btn-add"
                      onClick={() => {
                        setParticipantType('intern');
                        setShowAddParticipantModal(true);
                      }}
                    >
                      + Agregar Interno
                    </button>
                  </div>
                  <div className="tutors-grid">
                    {interns.length > 0 ? (
                      interns.map((intern) => (
                        <div key={intern.id} className="tutor-card">
                          <div className="tutor-icon">👨‍💼</div>
                          <h4>{intern.name}</h4>
                          <p>{intern.email}</p>
                          {intern.career && <p className="tutor-detail">{intern.career}</p>}
                          {intern.semester && <p className="tutor-detail">Semestre {intern.semester}</p>}
                        </div>
                      ))
                    ) : (
                      <p className="empty-message">No hay internos asignados</p>
                    )}
                  </div>
                </div>

                <div className="content-section">
                  <div className="section-header">
                    <h2>Tutor de Empresa</h2>
                    {!companyTutor && (
                      <button 
                        className="btn-add"
                        onClick={() => {
                          setParticipantType('company');
                          setShowAddParticipantModal(true);
                        }}
                      >
                        + Asignar Tutor de Empresa
                      </button>
                    )}
                  </div>
                  {companyTutor ? (
                    <div className="tutor-card">
                      <div className="tutor-icon">🏢</div>
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
                    className="btn-add"
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
                                <input type="checkbox" />
                                <span>{task.name}</span>
                              </div>
                            ))
                          ) : (
                            <p className="empty-message-small">Sin tareas. Agrega una nueva</p>
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
        </main>
      </div>

      {/* Modal: Add Participant */}
      {showAddParticipantModal && (
        <div className="modal-overlay" onClick={() => setShowAddParticipantModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Agregar Participante</h2>
            
            {/* Selector de tipo de participante */}
            <div className="participant-type-selector">
              <button 
                className={`type-btn ${participantType === 'academic' ? 'active' : ''}`}
                onClick={() => setParticipantType('academic')}
              >
                🎓 Tutor Académico
              </button>
              <button 
                className={`type-btn ${participantType === 'intern' ? 'active' : ''}`}
                onClick={() => setParticipantType('intern')}
              >
                👨‍💼 Interno
              </button>
            </div>

            {/* Formulario común */}
            <input
              type="text"
              placeholder="Nombre completo"
              value={participantForm.name}
              onChange={(e) => setParticipantForm({...participantForm, name: e.target.value})}
              className="modal-input"
            />
            <input
              type="email"
              placeholder="Correo electrónico"
              value={participantForm.email}
              onChange={(e) => setParticipantForm({...participantForm, email: e.target.value})}
              className="modal-input"
            />
            <input
              type="password"
              placeholder="Contraseña"
              value={participantForm.password}
              onChange={(e) => setParticipantForm({...participantForm, password: e.target.value})}
              className="modal-input"
            />

            {/* Campos específicos para Tutor Académico */}
            {participantType === 'academic' && (
              <input
                type="text"
                placeholder="Departamento"
                value={participantForm.department}
                onChange={(e) => setParticipantForm({...participantForm, department: e.target.value})}
                className="modal-input"
              />
            )}

            {/* Campos específicos para Interno */}
            {participantType === 'intern' && (
              <>
                <input
                  type="text"
                  placeholder="Carrera"
                  value={participantForm.career}
                  onChange={(e) => setParticipantForm({...participantForm, career: e.target.value})}
                  className="modal-input"
                />
                <input
                  type="number"
                  placeholder="Semestre"
                  value={participantForm.semester}
                  onChange={(e) => setParticipantForm({...participantForm, semester: e.target.value})}
                  className="modal-input"
                />
              </>
            )}

            <div className="modal-actions">
              <button className="btn-cancel" onClick={() => setShowAddParticipantModal(false)}>
                Cancelar
              </button>
              <button className="btn-submit" onClick={handleAddParticipant}>
                Agregar {participantType === 'academic' ? 'Tutor' : 'Interno'}
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
