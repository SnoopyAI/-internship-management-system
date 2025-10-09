import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import ProjectDetail from './components/ProjectDetail';
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
          
          {/* Redirigir cualquier ruta no encontrada al login */}
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
