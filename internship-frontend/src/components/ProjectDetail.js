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
  const [openParticipantAfterUniversity, setOpenParticipantAfterUniversity] = useState(false);
  
  // Estados para búsqueda de participantes existentes
  const [searchQuery, setSearchQuery] = useState('');
  const [availableParticipants, setAvailableParticipants] = useState([]);
  const [selectedParticipantId, setSelectedParticipantId] = useState(null);
  
  // Estados para formulario de participante
  const [participantForm, setParticipantForm] = useState({
    name: '',
    email: '',
    password: '',
    department: '', // Para tutor académico
    career: '', // Para interno
    semester: '', // Para interno
    universityId: null,
    academyTutorId: null, // Para interno
    companyTutorId: null  // Para interno
  });
  const [participantError, setParticipantError] = useState('');
  
  // Estados para universidades
  const [universities, setUniversities] = useState([]);
  const [showAddUniversityModal, setShowAddUniversityModal] = useState(false);
  const [newUniversityName, setNewUniversityName] = useState('');
  
  // Estados para listas y tareas
  const [lists, setLists] = useState([]);
  const [showAddListModal, setShowAddListModal] = useState(false);
  const [newListName, setNewListName] = useState('');
  const [showAddTaskModal, setShowAddTaskModal] = useState(false);
  const [selectedListId, setSelectedListId] = useState(null);
  const [taskForm, setTaskForm] = useState({
    title: '',
    description: '',
    dueDate: '',
    status: 'To Do'
  });
  const [showEditTaskModal, setShowEditTaskModal] = useState(false);
  const [selectedTask, setSelectedTask] = useState(null);
  
  // Estados para editar board
  const [showEditBoardModal, setShowEditBoardModal] = useState(false);
  const [editBoardForm, setEditBoardForm] = useState({
    name: '',
    description: ''
  });

  useEffect(() => {
    loadProjectDetails();
    loadUniversities();
  }, [id]);
  
  useEffect(() => {
    if (activeTab === 'tasks') {
      loadLists();
    }
  }, [activeTab, id]);

  // Cargar participantes disponibles cuando se abre el modal
  useEffect(() => {
    if (showAddParticipantModal) {
      loadAvailableParticipants();
    }
  }, [showAddParticipantModal, participantType]);

  const loadAvailableParticipants = async () => {
    const credentials = localStorage.getItem('authCredentials');
    let endpoint = '';
    
    if (participantType === 'academic') {
      endpoint = 'http://localhost:8080/academytutors/ReadAll';
    } else if (participantType === 'company') {
      endpoint = 'http://localhost:8080/companytutors/ReadAll';
    } else if (participantType === 'intern') {
      endpoint = 'http://localhost:8080/interns/ReadAll';
    }

    try {
      const response = await fetch(endpoint, {
        headers: {
          'Authorization': `Basic ${credentials}`
        }
      });

      if (response.ok) {
        const data = await response.json();
        // Filtrar participantes que NO estén ya en este board
        const filtered = data.filter(participant => {
          if (participantType === 'academic') {
            return !academicTutors.some(t => t.id === participant.id);
          } else if (participantType === 'company') {
            return companyTutor?.id !== participant.id;
          } else if (participantType === 'intern') {
            return !interns.some(i => i.id === participant.id);
          }
          return true;
        });
        setAvailableParticipants(filtered);
      }
    } catch (error) {
      console.error('Error loading available participants:', error);
    }
  };

  const loadUniversities = async () => {
    const credentials = localStorage.getItem('authCredentials');
    if (!credentials) return;

    try {
      const resp = await fetch(`http://localhost:8080/universities/ReadAll`, {
        headers: {
          'Authorization': `Basic ${credentials}`
        }
      });

      if (resp.ok) {
        const data = await resp.json();
        setUniversities(Array.isArray(data) ? data : []);
      }
    } catch (error) {
      console.error('Error loading universities:', error);
    }
  };

  const loadProjectDetails = async () => {
    const credentials = localStorage.getItem('authCredentials');
    if (!credentials) {
      navigate('/');
      return;
    }

    try {
      // Cargar detalles del proyecto con relaciones completas
      const response = await fetch(`http://localhost:8080/boards/${id}/details`, {
        headers: {
          'Authorization': `Basic ${credentials}`
        }
      });

      if (response.ok) {
        const data = await response.json();
        setProject(data);
        
        // Cargar tutores asociados
        if (data.academyTutors && Array.isArray(data.academyTutors)) {
          setAcademicTutors(data.academyTutors);
        } else {
          setAcademicTutors([]);
        }
        
        if (data.companyTutor) {
          setCompanyTutor(data.companyTutor);
        } else {
          setCompanyTutor(null);
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

  const handleAddParticipant = async () => {
    const credentials = localStorage.getItem('authCredentials');
    
    if (!selectedParticipantId) {
      setParticipantError('Por favor selecciona un participante');
      return;
    }
    
    try {
      let endpoint = '';
      
      if (participantType === 'academic') {
        // Añadir tutor académico al board
        endpoint = `http://localhost:8080/boards/${id}/academicTutor/${selectedParticipantId}`;
      } else if (participantType === 'company') {
        // Asignar tutor de empresa al board
        endpoint = `http://localhost:8080/boards/${id}/companyTutor/${selectedParticipantId}`;
      } else if (participantType === 'intern') {
        // Asignar board al interno (actualizar su boardId)
        endpoint = `http://localhost:8080/interns/update/${selectedParticipantId}`;
      }

      let response;
      
      if (participantType === 'intern') {
        // Para interns, necesitamos hacer PUT con el objeto completo
        const internResponse = await fetch(`http://localhost:8080/interns/Read/${selectedParticipantId}`, {
          headers: { 'Authorization': `Basic ${credentials}` }
        });
        const internData = await internResponse.json();
        
        response = await fetch(endpoint, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${credentials}`
          },
          body: JSON.stringify({
            ...internData,
            boardId: Number(id)
          })
        });
      } else {
        // Para tutors, usar PUT para actualizar la relación
        response = await fetch(endpoint, {
          method: 'PUT',
          headers: {
            'Authorization': `Basic ${credentials}`
          }
        });
      }

      if (response.ok) {
        setShowAddParticipantModal(false);
        setParticipantError('');
        setSelectedParticipantId(null);
        setSearchQuery('');
        loadProjectDetails(); // Recargar para actualizar la lista
      } else {
        const errorData = await response.text();
        setParticipantError('Error al añadir participante: ' + errorData);
      }
    } catch (error) {
      console.error('Error adding existing participant:', error);
      setParticipantError('Error al añadir participante');
    }
  };

  const handleAddUniversity = async () => {
    const credentials = localStorage.getItem('authCredentials');
    if (!newUniversityName.trim()) return;

    try {
      const response = await fetch(`http://localhost:8080/universities/add`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify({ name: newUniversityName })
      });

      if (response.ok) {
        const created = await response.json();
        setNewUniversityName('');
        setShowAddUniversityModal(false);
        // Recargar universidades y seleccionar la creada si viene con id
        await loadUniversities();
        if (created && created.id) {
          setParticipantForm(prev => ({ ...prev, universityId: created.id }));
          // If user tried to add participant but had no universities, reopen participant modal
          if (openParticipantAfterUniversity) {
            setOpenParticipantAfterUniversity(false);
            setShowAddParticipantModal(true);
          }
        }
      } else {
        const err = await response.text();
        alert('Error al crear universidad: ' + err);
      }
    } catch (error) {
      console.error('Error creating university:', error);
      alert('Error al crear universidad');
    }
  };

  const handleOpenAddParticipant = (type) => {
    setParticipantType(type);
    // If the participant being created requires a university (academic or intern)
    // and there are no universities, open Add University modal first
    if ((type === 'academic' || type === 'intern') && (!universities || universities.length === 0)) {
      setOpenParticipantAfterUniversity(true);
      setShowAddUniversityModal(true);
      return;
    }
    setShowAddParticipantModal(true);
  };

  const handleDeleteParticipant = async (participantId, type) => {
    if (!window.confirm(`¿Estás seguro de eliminar este ${type === 'academic' ? 'tutor académico' : type === 'intern' ? 'interno' : 'tutor de empresa'} del proyecto?`)) {
      return;
    }

    try {
      const token = localStorage.getItem('token');
      let endpoint = '';
      
      if (type === 'academic') {
        endpoint = `http://localhost:8080/boards/${id}/academicTutor/${participantId}`;
      } else if (type === 'company') {
        endpoint = `http://localhost:8080/boards/${id}/companyTutor`;
      } else if (type === 'intern') {
        endpoint = `http://localhost:8080/boards/${id}/intern/${participantId}`;
      }

      const response = await fetch(endpoint, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      if (!response.ok) {
        throw new Error('Error al eliminar participante');
      }

      // Recargar los detalles del proyecto
      await loadProjectDetails();
      alert('Participante eliminado exitosamente');
    } catch (error) {
      console.error('Error al eliminar participante:', error);
      alert('Error al eliminar participante');
    }
  };

  const handleOpenEditBoard = () => {
    setEditBoardForm({
      name: project?.name || '',
      description: project?.description || ''
    });
    setShowEditBoardModal(true);
  };

  const handleEditBoard = async () => {
    if (!editBoardForm.name.trim()) {
      alert('El nombre del proyecto es requerido');
      return;
    }

    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:8080/boards/${id}`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          name: editBoardForm.name,
          description: editBoardForm.description,
          startDate: project?.startDate || null,
          endDate: project?.endDate || null,
          companyTutorId: project?.companyTutorId || null
        })
      });

      if (!response.ok) {
        throw new Error('Error al actualizar el proyecto');
      }

      await loadProjectDetails();
      setShowEditBoardModal(false);
      alert('Proyecto actualizado exitosamente');
    } catch (error) {
      console.error('Error al actualizar proyecto:', error);
      alert('Error al actualizar proyecto');
    }
  };

  // ========== FUNCIONES DE LISTAS ==========
  const loadLists = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:8080/lists/board/${id}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.ok) {
        const listsData = await response.json();
        
        // Cargar las tareas de cada lista
        const listsWithTasks = await Promise.all(
          listsData.map(async (list) => {
            const tasksResponse = await fetch(`http://localhost:8080/tasks/list/${list.id}`, {
              headers: { 'Authorization': `Bearer ${token}` }
            });
            const tasks = tasksResponse.ok ? await tasksResponse.json() : [];
            return { ...list, tasks };
          })
        );
        
        setLists(listsWithTasks);
      }
    } catch (error) {
      console.error('Error loading lists:', error);
    }
  };

  const handleAddList = async () => {
    if (!newListName.trim()) {
      alert('El nombre de la lista es requerido');
      return;
    }

    try {
      const token = localStorage.getItem('token');
      const response = await fetch('http://localhost:8080/lists/add', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          name: newListName,
          boardId: parseInt(id)
        })
      });

      if (!response.ok) {
        throw new Error('Error al crear la lista');
      }

      setNewListName('');
      setShowAddListModal(false);
      await loadLists();
    } catch (error) {
      console.error('Error creating list:', error);
      alert('Error al crear la lista');
    }
  };

  const handleDeleteList = async (listId) => {
    if (!window.confirm('¿Estás seguro de eliminar esta lista y todas sus tareas?')) {
      return;
    }

    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:8080/lists/${listId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error('Error al eliminar la lista');
      }

      await loadLists();
    } catch (error) {
      console.error('Error deleting list:', error);
      alert('Error al eliminar la lista');
    }
  };

  // ========== FUNCIONES DE TAREAS ==========
  const handleAddTask = async () => {
    if (!taskForm.title.trim()) {
      alert('El título de la tarea es requerido');
      return;
    }

    try {
      const token = localStorage.getItem('token');
      const userData = JSON.parse(localStorage.getItem('userData') || '{}');
      
      const response = await fetch('http://localhost:8080/tasks/add', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          title: taskForm.title,
          description: taskForm.description,
          dueDate: taskForm.dueDate || null,
          status: taskForm.status || 'To Do',
          listId: selectedListId,
          createdByTutorId: userData.id || 1,
          internIds: []
        })
      });

      if (!response.ok) {
        throw new Error('Error al crear la tarea');
      }

      setTaskForm({ title: '', description: '', dueDate: '', status: 'To Do' });
      setShowAddTaskModal(false);
      await loadLists();
    } catch (error) {
      console.error('Error creating task:', error);
      alert('Error al crear la tarea');
    }
  };

  const handleEditTask = (task) => {
    setSelectedTask(task);
    setTaskForm({
      title: task.title,
      description: task.description || '',
      dueDate: task.dueDate || '',
      status: task.status || 'To Do'
    });
    setShowEditTaskModal(true);
  };

  const handleUpdateTask = async () => {
    if (!taskForm.title.trim()) {
      alert('El título de la tarea es requerido');
      return;
    }

    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:8080/tasks/${selectedTask.taskId}`, {
        method: 'PATCH',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          title: taskForm.title,
          description: taskForm.description,
          dueDate: taskForm.dueDate || null,
          status: taskForm.status,
          listId: selectedTask.listId,
          createdByTutorId: selectedTask.createdByTutorId,
          internIds: selectedTask.internIds || []
        })
      });

      if (!response.ok) {
        throw new Error('Error al actualizar la tarea');
      }

      setTaskForm({ title: '', description: '', dueDate: '', status: 'To Do' });
      setShowEditTaskModal(false);
      setSelectedTask(null);
      await loadLists();
    } catch (error) {
      console.error('Error updating task:', error);
      alert('Error al actualizar la tarea');
    }
  };

  const handleDeleteTask = async (taskId) => {
    if (!window.confirm('¿Estás seguro de eliminar esta tarea?')) {
      return;
    }

    try {
      const token = localStorage.getItem('token');
      const response = await fetch(`http://localhost:8080/tasks/${taskId}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error('Error al eliminar la tarea');
      }

      await loadLists();
    } catch (error) {
      console.error('Error deleting task:', error);
      alert('Error al eliminar la tarea');
    }
  };

  if (loading) {
    return (
      <div className="loading-screen">
        <div className="spinner"></div>
        <p className="loading-text">Loading</p>
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
          <button onClick={() => navigate('/dashboard')} className="nav-item active">Inicio</button>
          <button onClick={() => navigate('/participants')} className="nav-item">Participantes</button>
          <button onClick={() => navigate('/universities')} className="nav-item">Universidades</button>
          <button onClick={() => navigate('/settings')} className="nav-item">Configuración</button>
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
            <div className="project-title-section">
              <h2 className="sidebar-project-name">{project.name}</h2>
              <button 
                className="btn-edit-board" 
                onClick={handleOpenEditBoard}
                title="Editar proyecto"
              >
                ✎
              </button>
            </div>
            <p className="sidebar-description">{project.description || 'Sin descripción'}</p>
          </div>

          <div className="sidebar-section">
            <h3 className="sidebar-heading">Información</h3>
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
            <h3 className="sidebar-heading">Participantes</h3>
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
              onClick={() => handleOpenAddParticipant('academic')}
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
              Información
            </button>
            <button 
              className={`tab ${activeTab === 'tutors' ? 'active' : ''}`}
              onClick={() => setActiveTab('tutors')}
            >
              Participantes
            </button>
            <button 
              className={`tab ${activeTab === 'tasks' ? 'active' : ''}`}
              onClick={() => setActiveTab('tasks')}
            >
              Tablero de Tareas
            </button>
          </div>

          {/* Tab Content */}
          <div className="tab-content-area">
            {/* Overview Tab */}
            {activeTab === 'overview' && (
              <div className="tab-content">
                <div className="content-card">
                  <h2>Descripción del Proyecto</h2>
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
                      onClick={() => handleOpenAddParticipant('academic')}
                    >
                      + Agregar Tutor Académico
                    </button>
                  </div>
                  <div className="tutors-grid">
                    {academicTutors.length > 0 ? (
                      academicTutors.map((tutor) => (
                        <div key={tutor.id} className="tutor-card">
                          <div className="tutor-card-content">
                            <h4>{tutor.name}</h4>
                            <p>{tutor.email}</p>
                            {tutor.department && <p className="tutor-detail">{tutor.department}</p>}
                          </div>
                          <button 
                            className="btn-delete-participant"
                            onClick={() => handleDeleteParticipant(tutor.id, 'academic')}
                            title="Eliminar tutor"
                          >
                            ✕
                          </button>
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
                      onClick={() => handleOpenAddParticipant('intern')}
                    >
                      + Agregar Interno
                    </button>
                  </div>
                  <div className="tutors-grid">
                    {interns.length > 0 ? (
                      interns.map((intern) => (
                        <div key={intern.id} className="tutor-card">
                          <div className="tutor-card-content">
                            <h4>{intern.name}</h4>
                            <p>{intern.email}</p>
                            {intern.career && <p className="tutor-detail">{intern.career}</p>}
                            {intern.semester && <p className="tutor-detail">Semestre {intern.semester}</p>}
                          </div>
                          <button 
                            className="btn-delete-participant"
                            onClick={() => handleDeleteParticipant(intern.id, 'intern')}
                            title="Eliminar interno"
                          >
                            ✕
                          </button>
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
                        onClick={() => handleOpenAddParticipant('company')}
                      >
                        + Asignar Tutor de Empresa
                      </button>
                    )}
                  </div>
                  {companyTutor ? (
                    <div className="tutor-card">
                      <div className="tutor-card-content">
                        <h4>{companyTutor.name}</h4>
                        <p>{companyTutor.email}</p>
                      </div>
                      <button 
                        className="btn-delete-participant"
                        onClick={() => handleDeleteParticipant(companyTutor.id, 'company')}
                        title="Eliminar tutor de empresa"
                      >
                        ✕
                      </button>
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
                          <div className="list-actions">
                            <button 
                              className="btn-add-task"
                              onClick={() => {
                                setSelectedListId(list.id);
                                setTaskForm({ title: '', description: '', dueDate: '', status: 'To Do' });
                                setShowAddTaskModal(true);
                              }}
                              title="Agregar tarea"
                            >
                              +
                            </button>
                            <button 
                              className="btn-delete-list"
                              onClick={() => handleDeleteList(list.id)}
                              title="Eliminar lista"
                            >
                              ✕
                            </button>
                          </div>
                        </div>
                        <div className="tasks-list">
                          {list.tasks && list.tasks.length > 0 ? (
                            list.tasks.map((task) => (
                              <div key={task.taskId} className="task-item" onClick={() => handleEditTask(task)}>
                                <div className="task-content">
                                  <h4>{task.title}</h4>
                                  <span className={`task-status status-${task.status.toLowerCase().replace(' ', '-')}`}>
                                    {task.status}
                                  </span>
                                </div>
                                <button 
                                  className="btn-delete-task"
                                  onClick={(e) => {
                                    e.stopPropagation();
                                    handleDeleteTask(task.taskId);
                                  }}
                                  title="Eliminar tarea"
                                >
                                  ✕
                                </button>
                              </div>
                            ))
                          ) : (
                            <p className="empty-message-small">Sin tareas</p>
                          )}
                        </div>
                      </div>
                    ))
                  ) : (
                    <p className="empty-message">No hay listas. Crea una lista para comenzar.</p>
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
            <div className="modal-body-scroll">
            {participantError && <div className="modal-error">{participantError}</div>}
            
            {/* Selector de tipo de participante */}
            <div className="participant-type-selector">
              <button 
                className={`type-btn ${participantType === 'academic' ? 'active' : ''}`}
                onClick={() => {
                  setParticipantType('academic');
                  setSelectedParticipantId(null);
                  setSearchQuery('');
                }}
              >
                Tutor Académico
              </button>
              <button 
                className={`type-btn ${participantType === 'company' ? 'active' : ''}`}
                onClick={() => {
                  setParticipantType('company');
                  setSelectedParticipantId(null);
                  setSearchQuery('');
                }}
              >
                Tutor Empresa
              </button>
              <button 
                className={`type-btn ${participantType === 'intern' ? 'active' : ''}`}
                onClick={() => {
                  setParticipantType('intern');
                  setSelectedParticipantId(null);
                  setSearchQuery('');
                }}
              >
                Interno
              </button>
            </div>

            {/* Búsqueda y selección de participantes existentes */}
            <div className="search-container">
              <input
                type="text"
                placeholder={`Buscar por nombre o email...`}
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="modal-input search-input"
              />
            </div>

            <div className="participants-list">
              {availableParticipants.length > 0 ? (
                availableParticipants
                  .filter(p => 
                    p.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
                    p.email.toLowerCase().includes(searchQuery.toLowerCase())
                  )
                  .map((participant) => (
                    <div 
                      key={participant.id} 
                      className={`participant-item ${selectedParticipantId === participant.id ? 'selected' : ''}`}
                      onClick={() => setSelectedParticipantId(participant.id)}
                    >
                      <div className="participant-info">
                        <div className="participant-details">
                          <strong>{participant.name}</strong>
                          <span className="participant-email">{participant.email}</span>
                          {participant.career && <span className="participant-extra">{participant.career}</span>}
                          {participant.department && <span className="participant-extra">{participant.department}</span>}
                        </div>
                      </div>
                      {selectedParticipantId === participant.id && (
                        <div className="selected-check">✓</div>
                      )}
                    </div>
                  ))
              ) : (
                <p className="empty-search">
                  No hay {participantType === 'academic' ? 'tutores académicos' : participantType === 'company' ? 'tutores de empresa' : 'internos'} disponibles para añadir
                </p>
              )}
            </div>

            </div>
            
            <div className="modal-actions">
              <button className="btn-cancel" onClick={() => setShowAddParticipantModal(false)}>
                Cancelar
              </button>
              <button className="btn-submit" onClick={handleAddParticipant}>
                Añadir {participantType === 'academic' ? 'Tutor Académico' : participantType === 'company' ? 'Tutor de Empresa' : 'Interno'}
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
            <div className="modal-body-scroll">
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
        </div>
      )}

      {/* Modal: Add Task */}
      {showAddTaskModal && (
        <div className="modal-overlay" onClick={() => setShowAddTaskModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Nueva Tarea</h2>
            <div className="modal-body-scroll">
              <input
                type="text"
                placeholder="Título de la tarea *"
                value={taskForm.title}
                onChange={(e) => setTaskForm({...taskForm, title: e.target.value})}
                className="modal-input"
              />
              <textarea
                placeholder="Descripción"
                value={taskForm.description}
                onChange={(e) => setTaskForm({...taskForm, description: e.target.value})}
                className="modal-input modal-textarea"
                rows="3"
              />
              <input
                type="date"
                value={taskForm.dueDate}
                onChange={(e) => setTaskForm({...taskForm, dueDate: e.target.value})}
                className="modal-input"
              />
              <select
                value={taskForm.status}
                onChange={(e) => setTaskForm({...taskForm, status: e.target.value})}
                className="modal-input"
              >
                <option value="To Do">Por Hacer</option>
                <option value="In Progress">En Progreso</option>
                <option value="Done">Completada</option>
              </select>
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
        </div>
      )}

      {/* Modal: Edit Task */}
      {showEditTaskModal && (
        <div className="modal-overlay" onClick={() => setShowEditTaskModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Editar Tarea</h2>
            <div className="modal-body-scroll">
              <input
                type="text"
                placeholder="Título de la tarea *"
                value={taskForm.title}
                onChange={(e) => setTaskForm({...taskForm, title: e.target.value})}
                className="modal-input"
              />
              <textarea
                placeholder="Descripción"
                value={taskForm.description}
                onChange={(e) => setTaskForm({...taskForm, description: e.target.value})}
                className="modal-input modal-textarea"
                rows="3"
              />
              <input
                type="date"
                value={taskForm.dueDate}
                onChange={(e) => setTaskForm({...taskForm, dueDate: e.target.value})}
                className="modal-input"
              />
              <select
                value={taskForm.status}
                onChange={(e) => setTaskForm({...taskForm, status: e.target.value})}
                className="modal-input"
              >
                <option value="To Do">Por Hacer</option>
                <option value="In Progress">En Progreso</option>
                <option value="Done">Completada</option>
              </select>
              <div className="modal-actions">
                <button className="btn-cancel" onClick={() => setShowEditTaskModal(false)}>
                  Cancelar
                </button>
                <button className="btn-submit" onClick={handleUpdateTask}>
                  Guardar Cambios
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Modal: Edit Board */}
      {showEditBoardModal && (
        <div className="modal-overlay" onClick={() => setShowEditBoardModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Editar Proyecto</h2>
            <div className="modal-body-scroll">
              <input
                type="text"
                placeholder="Nombre del proyecto"
                value={editBoardForm.name}
                onChange={(e) => setEditBoardForm({...editBoardForm, name: e.target.value})}
                className="modal-input"
              />
              <textarea
                placeholder="Descripción del proyecto"
                value={editBoardForm.description}
                onChange={(e) => setEditBoardForm({...editBoardForm, description: e.target.value})}
                className="modal-input modal-textarea"
                rows="4"
              />
              <div className="modal-actions">
                <button className="btn-cancel" onClick={() => setShowEditBoardModal(false)}>
                  Cancelar
                </button>
                <button className="btn-submit" onClick={handleEditBoard}>
                  Guardar Cambios
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Modal: Add University */}
      {showAddUniversityModal && (
        <div className="modal-overlay" onClick={() => setShowAddUniversityModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Agregar Universidad</h2>
            <div className="modal-body-scroll">
            <input
              type="text"
              placeholder="Nombre de la universidad"
              value={newUniversityName}
              onChange={(e) => setNewUniversityName(e.target.value)}
              className="modal-input"
            />
            <div className="modal-actions">
              <button className="btn-cancel" onClick={() => setShowAddUniversityModal(false)}>
                Cancelar
              </button>
              <button className="btn-submit" onClick={handleAddUniversity}>
                Crear Universidad
              </button>
            </div>
            </div>
          </div>
        </div>
      )}

      {/* Modal: Edit Board */}
      {showEditBoardModal && (
        <div className="modal-overlay" onClick={() => setShowEditBoardModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Editar Proyecto</h2>
            <div className="modal-body-scroll">
              <input
                type="text"
                placeholder="Nombre del proyecto"
                value={editBoardForm.name}
                onChange={(e) => setEditBoardForm({...editBoardForm, name: e.target.value})}
                className="modal-input"
              />
              <textarea
                placeholder="Descripción del proyecto"
                value={editBoardForm.description}
                onChange={(e) => setEditBoardForm({...editBoardForm, description: e.target.value})}
                className="modal-input modal-textarea"
                rows="4"
              />
              <div className="modal-actions">
                <button className="btn-cancel" onClick={() => setShowEditBoardModal(false)}>
                  Cancelar
                </button>
                <button className="btn-submit" onClick={handleEditBoard}>
                  Guardar Cambios
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default ProjectDetail;
