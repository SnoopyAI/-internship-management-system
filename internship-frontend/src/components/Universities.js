import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Universities.css';

function Universities() {
  const navigate = useNavigate();
  const [universities, setUniversities] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [selectedUniversity, setSelectedUniversity] = useState(null);
  const [formData, setFormData] = useState({ name: '' });
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadUniversities();
  }, []);

  const loadUniversities = async () => {
    const credentials = localStorage.getItem('authCredentials');
    if (!credentials) {
      navigate('/');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/universities/ReadAll', {
        headers: {
          'Authorization': `Basic ${credentials}`
        }
      });

      if (response.ok) {
        const data = await response.json();
        setUniversities(Array.isArray(data) ? data : []);
      }
    } catch (error) {
      console.error('Error loading universities:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleAddUniversity = async () => {
    if (!formData.name.trim()) return;

    const credentials = localStorage.getItem('authCredentials');
    try {
      const response = await fetch('http://localhost:8080/universities/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify({ name: formData.name })
      });

      if (response.ok) {
        setFormData({ name: '' });
        setShowAddModal(false);
        loadUniversities();
      }
    } catch (error) {
      console.error('Error creating university:', error);
    }
  };

  const handleEditUniversity = async () => {
    if (!formData.name.trim() || !selectedUniversity) return;

    const credentials = localStorage.getItem('authCredentials');
    try {
      const response = await fetch(`http://localhost:8080/universities/${selectedUniversity.id}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${credentials}`
        },
        body: JSON.stringify({ name: formData.name })
      });

      if (response.ok) {
        setFormData({ name: '' });
        setShowEditModal(false);
        setSelectedUniversity(null);
        loadUniversities();
      }
    } catch (error) {
      console.error('Error updating university:', error);
    }
  };

  const handleDeleteUniversity = async (id) => {
    if (!window.confirm('¿Estás seguro de eliminar esta universidad?')) return;

    const credentials = localStorage.getItem('authCredentials');
    try {
      const response = await fetch(`http://localhost:8080/universities/${id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Basic ${credentials}`
        }
      });

      if (response.ok) {
        loadUniversities();
      }
    } catch (error) {
      console.error('Error deleting university:', error);
    }
  };

  const openEditModal = (university) => {
    setSelectedUniversity(university);
    setFormData({ name: university.name });
    setShowEditModal(true);
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

  const filteredUniversities = universities.filter(u =>
    u.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return (
      <div className="loading-screen">
        <div className="spinner"></div>
        <p className="loading-text">Loading</p>
      </div>
    );
  }

  return (
    <div className="universities-container">
      {/* Header */}
      <header className="top-header">
        <div className="header-brand">
          <h2>SGP</h2>
          <span className="brand-subtitle">Sistema de Gestión</span>
        </div>
        
        <nav className="top-nav">
          <button onClick={() => navigate('/dashboard')} className="nav-item">Inicio</button>
          <button onClick={() => navigate('/participants')} className="nav-item">Equipo</button>
          <button onClick={() => navigate('/universities')} className="nav-item active">Universidades</button>
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
      <main className="universities-main">
        <div className="page-header">
          <div>
            <h1>Universidades</h1>
            <p className="page-subtitle">Gestiona las universidades del sistema</p>
          </div>
          <button className="btn-primary" onClick={() => setShowAddModal(true)}>
            + Nueva Universidad
          </button>
        </div>

        {/* Search Bar */}
        <div className="search-bar">
          <input
            type="text"
            placeholder="Buscar universidad..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
        </div>

        {/* Universities Grid */}
        <div className="universities-grid">
          {filteredUniversities.length > 0 ? (
            filteredUniversities.map((university) => (
              <div key={university.id} className="university-card">
                <h3>{university.name}</h3>
                <div className="university-actions">
                  <button 
                    className="btn-edit" 
                    onClick={() => openEditModal(university)}
                  >
                    Editar
                  </button>
                  <button 
                    className="btn-delete" 
                    onClick={() => handleDeleteUniversity(university.id)}
                  >
                    Eliminar
                  </button>
                </div>
              </div>
            ))
          ) : (
            <div className="empty-state">
              <p>No se encontraron universidades</p>
            </div>
          )}
        </div>
      </main>

      {/* Add Modal */}
      {showAddModal && (
        <div className="modal-overlay" onClick={() => setShowAddModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Nueva Universidad</h2>
            <div className="modal-body-scroll">
              <input
                type="text"
                placeholder="Nombre de la universidad"
                value={formData.name}
                onChange={(e) => setFormData({ name: e.target.value })}
                className="modal-input"
              />
              <div className="modal-actions">
                <button className="btn-cancel" onClick={() => setShowAddModal(false)}>
                  Cancelar
                </button>
                <button className="btn-submit" onClick={handleAddUniversity}>
                  Crear
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Edit Modal */}
      {showEditModal && (
        <div className="modal-overlay" onClick={() => setShowEditModal(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>Editar Universidad</h2>
            <div className="modal-body-scroll">
              <input
                type="text"
                placeholder="Nombre de la universidad"
                value={formData.name}
                onChange={(e) => setFormData({ name: e.target.value })}
                className="modal-input"
              />
              <div className="modal-actions">
                <button className="btn-cancel" onClick={() => setShowEditModal(false)}>
                  Cancelar
                </button>
                <button className="btn-submit" onClick={handleEditUniversity}>
                  Guardar
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default Universities;
