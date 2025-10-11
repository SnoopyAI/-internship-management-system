import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import ProjectDetail from './components/ProjectDetail';
import Universities from './components/Universities';
import Participants from './components/Participants';
import AllParticipants from './components/AllParticipants';
import Tasks from './components/Tasks';
import Reports from './components/Reports';
import Settings from './components/Settings';
import './App.css';

function App() {
  // Verificar si el usuario está autenticado
  const isAuthenticated = () => {
    return localStorage.getItem('userEmail') !== null;
  };

  // Componente para proteger rutas
  const PrivateRoute = ({ children }) => {
    return isAuthenticated() ? children : <Navigate to="/" />;
  };

  return (
    <Router>
      <div className="App">
        <Routes>
          {/* Ruta de login */}
          <Route path="/" element={<Login />} />
          
          {/* Ruta de registro */}
          <Route path="/register" element={<Register />} />
          
          {/* Ruta protegida del dashboard */}
          <Route 
            path="/dashboard" 
            element={
              <PrivateRoute>
                <Dashboard />
              </PrivateRoute>
            } 
          />
          
          {/* Ruta protegida de detalle del proyecto */}
          <Route 
            path="/project/:id" 
            element={
              <PrivateRoute>
                <ProjectDetail />
              </PrivateRoute>
            } 
          />

          {/* Ruta de universidades */}
          <Route 
            path="/universities" 
            element={
              <PrivateRoute>
                <Universities />
              </PrivateRoute>
            } 
          />

          {/* Ruta de participantes */}
          <Route 
            path="/participants" 
            element={
              <PrivateRoute>
                <AllParticipants />
              </PrivateRoute>
            } 
          />

          {/* Ruta de tareas */}
          <Route 
            path="/tasks" 
            element={
              <PrivateRoute>
                <Tasks />
              </PrivateRoute>
            } 
          />

          {/* Ruta de reportes */}
          <Route 
            path="/reports" 
            element={
              <PrivateRoute>
                <Reports />
              </PrivateRoute>
            } 
          />

          {/* Ruta de configuración */}
          <Route 
            path="/settings" 
            element={
              <PrivateRoute>
                <Settings />
              </PrivateRoute>
            } 
          />
          
          {/* Redirigir cualquier ruta no encontrada al login */}
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
