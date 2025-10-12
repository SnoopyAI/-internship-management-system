import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './AllParticipants.css';

function AllParticipants() {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState('interns');
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  
  // Estados para cada tipo
  const [interns, setInterns] = useState([]);
  const [academicTutors, setAcademicTutors] = useState([]);
  const [companyTutors, setCompanyTutors] = useState([]);
  
  // Estados de modales
  const [showEditModal, setShowEditModal] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);
  const [editForm, setEditForm] = useState({});

  useEffect(() => {
    loadData();
  }, [activeTab]);

  const loadData = async () => {
    setLoading(true);
    const credentials = localStorage.getItem('authCredentials');
    
    try {
      if (activeTab === 'interns') {
        const response = await fetch('http://localhost:8080/interns/ReadAll', {
          headers: { 'Authorization': `Basic ${credentials}` }
        });
        if (response.ok) {
          const data = await response.json();
          setInterns(Array.isArray(data) ? data : []);
        }
      } else if (activeTab === 'academic') {
        const response = await fetch('http://localhost:8080/academytutors/ReadAll', {
          headers: { 'Authorization': `Basic ${credentials}` }
        });
        if (response.ok) {
          const data = await response.json();
          setAcademicTutors(Array.isArray(data) ? data : []);
        }
      } else if (activeTab === 'company') {
        const response = await fetch('http://localhost:8080/companytutors/ReadAll', {
          headers: { 'Authorization': `Basic ${credentials}` }
        });
        if (response.ok) {
          const data = await response.json();
          setCompanyTutors(Array.isArray(data) ? data : []);
        }
      }
    } catch (error) {
      console.error('Error loading data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (item) => {
    setSelectedItem(item);
    setEditForm({ ...item });
    setShowEditModal(true);
  };

  const handleUpdate = async () => {
    const credentials = localStorage.getItem('authCredentials');
    let endpoint = '';
    
    if (activeTab === 'interns') {
      endpoint = `http://localhost:8080/interns/update/${selectedItem.id}`;
    } else if (activeTab === 'academic') {
      endpoint = `http://localhost:8080/academytutors/${selectedItem.id}`;
    } else if (activeTab === 'company') {
      endpoint = `http://localhost:8080/companytutors/${selectedItem.id}`;
    }

    try {
      const response = await fetch(endpoint, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify(editForm)
      });

      if (response.ok) {
        setShowEditModal(false);
        loadData();
      } else {
        alert('Error al actualizar');
      }
    } catch (error) {
      console.error('Error updating:', error);
      alert('Error al actualizar');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('¿Estás seguro de eliminar este elemento?')) return;
    
    const credentials = localStorage.getItem('authCredentials');
    let endpoint = '';
    
    if (activeTab === 'interns') {
      endpoint = `http://localhost:8080/interns/${id}`;
    } else if (activeTab === 'academic') {
      endpoint = `http://localhost:8080/academytutors/${id}`;
    } else if (activeTab === 'company') {
      endpoint = `http://localhost:8080/companytutors/${id}`;
    }

    try {
      const response = await fetch(endpoint, {
        method: 'DELETE',
        headers: { 'Authorization': `Basic ${credentials}` }
      });

      if (response.ok) {
        loadData();
      } else {
        alert('Error al eliminar');
      }
    } catch (error) {
      console.error('Error deleting:', error);
      alert('Error al eliminar');
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

  const getCurrentData = () => {
    if (activeTab === 'interns') return interns;
    if (activeTab === 'academic') return academicTutors;
    if (activeTab === 'company') return companyTutors;
    return [];
  };

  const filteredData = getCurrentData().filter(item =>
    item.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.email?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="all-participants-container">
      {/* Header */}
      <header className="top-header">
        <div className="header-brand">
          <h2>SGP</h2>
          <span className="brand-subtitle">Sistema de Gestión</span>
        </div>
        
        <nav className="top-nav">
          <button onClick={() => navigate('/dashboard')} className="nav-item">Inicio</button>
          <button onClick={() => navigate('/participants')} className="nav-item active">Participantes</button>
          <button onClick={() => navigate('/universities')} className="nav-item">Universidades</button>
          <button onClick={() => navigate('/settings')} className="nav-item">Configuración</button>
        </nav>

        <div className="header-actions">
          <button onClick={handleLogout} className="logout-btn">Cerrar Sesión</button>
        </div>
      </header>

      {/* Main Content */}
      <main className="participants-main">
        <div className="page-header">
          <h1>Gestión de Participantes</h1>
          <p className="page-subtitle">Administra internos y tutores del sistema</p>
        </div>

        {/* Tabs */}
        <div className="tabs-container">
          <button
            className={`tab-btn ${activeTab === 'interns' ? 'active' : ''}`}
            onClick={() => setActiveTab('interns')}
          >
            Internos
          </button>
          <button
            className={`tab-btn ${activeTab === 'academic' ? 'active' : ''}`}
            onClick={() => setActiveTab('academic')}
          >
            Tutores Académicos
          </button>
          <button
            className={`tab-btn ${activeTab === 'company' ? 'active' : ''}`}
            onClick={() => setActiveTab('company')}
          >
            Tutores de Empresa
          </button>
        </div>

        {/* Search */}
        <div className="search-section">
          <input
            type="text"
            placeholder="Buscar por nombre o email..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
        </div>

        {/* Table */}
        <div className="table-container">
          {loading ? (
            <div className="loading-state">Cargando...</div>
          ) : filteredData.length === 0 ? (
            <div className="empty-state">No hay datos disponibles</div>
          ) : (
            <table className="data-table">
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Email</th>
                  {activeTab === 'interns' && <th>Carrera</th>}
                  {activeTab === 'academic' && <th>Departamento</th>}
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                {filteredData.map((item) => (
                  <tr key={item.id}>
                    <td>{item.name}</td>
                    <td>{item.email}</td>
                    {activeTab === 'interns' && <td>{item.career || 'N/A'}</td>}
                    {activeTab === 'academic' && <td>{item.department || 'N/A'}</td>}
                    <td>
                      <div className="action-buttons">
                        <button onClick={() => handleEdit(item)} className="btn-edit">
                          Editar
                        </button>
                        <button onClick={() => handleDelete(item.id)} className="btn-delete">
                          Eliminar
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </main>

      {/* Edit Modal */}
      {showEditModal && (
        <div className="modal-overlay" onClick={() => setShowEditModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Editar {activeTab === 'interns' ? 'Interno' : 'Tutor'}</h2>
            <div className="modal-body">
              <div className="form-group">
                <label>Nombre</label>
                <input
                  type="text"
                  value={editForm.name || ''}
                  onChange={(e) => setEditForm({ ...editForm, name: e.target.value })}
                  className="form-input"
                />
              </div>
              <div className="form-group">
                <label>Email</label>
                <input
                  type="email"
                  value={editForm.email || ''}
                  onChange={(e) => setEditForm({ ...editForm, email: e.target.value })}
                  className="form-input"
                />
              </div>
              {activeTab === 'interns' && (
                <div className="form-group">
                  <label>Carrera</label>
                  <input
                    type="text"
                    value={editForm.career || ''}
                    onChange={(e) => setEditForm({ ...editForm, career: e.target.value })}
                    className="form-input"
                  />
                </div>
              )}
              {activeTab === 'academic' && (
                <div className="form-group">
                  <label>Departamento</label>
                  <input
                    type="text"
                    value={editForm.department || ''}
                    onChange={(e) => setEditForm({ ...editForm, department: e.target.value })}
                    className="form-input"
                  />
                </div>
              )}
            </div>
            <div className="modal-actions">
              <button onClick={() => setShowEditModal(false)} className="btn-cancel">
                Cancelar
              </button>
              <button onClick={handleUpdate} className="btn-submit">
                Guardar
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default AllParticipants;
