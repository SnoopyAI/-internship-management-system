import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Participants.css';

function Participants() {
  const navigate = useNavigate();
  const [academicTutors, setAcademicTutors] = useState([]);
  const [companyTutors, setCompanyTutors] = useState([]);
  const [interns, setInterns] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState('all'); // all, academic, company, intern
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadAllParticipants();
  }, []);

  const loadAllParticipants = async () => {
    const credentials = localStorage.getItem('authCredentials');
    if (!credentials) {
      navigate('/');
      return;
    }

    try {
      // Load academic tutors
      const academicResp = await fetch('http://localhost:8080/academytutors/ReadAll', {
        headers: { 'Authorization': `Basic ${credentials}` }
      });
      if (academicResp.ok) {
        const data = await academicResp.json();
        setAcademicTutors(data);
      }

      // Load company tutors
      const companyResp = await fetch('http://localhost:8080/companytutors/ReadAll', {
        headers: { 'Authorization': `Basic ${credentials}` }
      });
      if (companyResp.ok) {
        const data = await companyResp.json();
        setCompanyTutors(data);
      }

      // Load interns
      const internsResp = await fetch('http://localhost:8080/interns/ReadAll', {
        headers: { 'Authorization': `Basic ${credentials}` }
      });
      if (internsResp.ok) {
        const data = await internsResp.json();
        setInterns(data);
      }
    } catch (error) {
      console.error('Error loading participants:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate('/');
  };

  const getAllParticipants = () => {
    const all = [];
    
    if (filter === 'all' || filter === 'academic') {
      academicTutors.forEach(t => all.push({
        ...t,
        type: 'academic',
        typeLabel: 'Tutor Académico',
        icon: '🎓'
      }));
    }
    
    if (filter === 'all' || filter === 'company') {
      companyTutors.forEach(t => all.push({
        ...t,
        type: 'company',
        typeLabel: 'Tutor de Empresa',
        icon: '🏢'
      }));
    }
    
    if (filter === 'all' || filter === 'intern') {
      interns.forEach(i => all.push({
        ...i,
        type: 'intern',
        typeLabel: 'Interno',
        icon: '👨‍💼'
      }));
    }

    return all.filter(p =>
      p.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      p.email.toLowerCase().includes(searchTerm.toLowerCase())
    );
  };

  const participants = getAllParticipants();

  if (loading) {
    return (
      <div className="loading-screen">
        <div className="spinner"></div>
        <p className="loading-text">Loading</p>
      </div>
    );
  }

  return (
    <div className="participants-container">
      {/* Header */}
      <header className="top-header">
        <div className="header-brand">
          <h2>SGP</h2>
          <span className="brand-subtitle">Sistema de Gestión</span>
        </div>
        
        <nav className="top-nav">
          <button onClick={() => navigate('/dashboard')} className="nav-item">Inicio</button>
          <button onClick={() => navigate('/participants')} className="nav-item active">Equipo</button>
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

      {/* Main Content */}
      <main className="participants-main">
        <div className="page-header">
          <div>
            <h1>Participantes</h1>
            <p className="page-subtitle">
              Total: {participants.length} participantes
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
              Todos ({academicTutors.length + companyTutors.length + interns.length})
            </button>
            <button 
              className={`filter-btn ${filter === 'academic' ? 'active' : ''}`}
              onClick={() => setFilter('academic')}
            >
              Tutores Académicos ({academicTutors.length})
            </button>
            <button 
              className={`filter-btn ${filter === 'company' ? 'active' : ''}`}
              onClick={() => setFilter('company')}
            >
              Tutores de Empresa ({companyTutors.length})
            </button>
            <button 
              className={`filter-btn ${filter === 'intern' ? 'active' : ''}`}
              onClick={() => setFilter('intern')}
            >
              Internos ({interns.length})
            </button>
          </div>

          <input
            type="text"
            placeholder="Buscar por nombre o email..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
        </div>

        {/* Participants Grid */}
        <div className="participants-grid">
          {participants.length > 0 ? (
            participants.map((participant) => (
              <div key={`${participant.type}-${participant.id}`} className="participant-card">
                <div className="participant-info">
                  <h3>{participant.name}</h3>
                  <p className="participant-email">{participant.email}</p>
                  <span className={`participant-badge ${participant.type}`}>
                    {participant.typeLabel}
                  </span>
                  {participant.department && (
                    <p className="participant-detail">{participant.department}</p>
                  )}
                  {participant.position && (
                    <p className="participant-detail">{participant.position}</p>
                  )}
                  {participant.career && (
                    <p className="participant-detail">{participant.career}</p>
                  )}
                  {participant.semester && (
                    <p className="participant-detail">Semestre {participant.semester}</p>
                  )}
                </div>
              </div>
            ))
          ) : (
            <div className="empty-state">
              <p>No se encontraron participantes</p>
            </div>
          )}
        </div>
      </main>
    </div>
  );
}

export default Participants;
