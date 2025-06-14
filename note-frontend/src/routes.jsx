import { Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Notes from './pages/Notes';
import AddNotePage from './pages/AddNotePage';
import NotesEditPage from './pages/NotesEditPage'; 

const AppRoutes = () => (
  <Routes>
    <Route path="/" element={<Navigate to="/login" replace />} />

    <Route path="/login" element={<Login />} />
    <Route path="/register" element={<Register />} />
    <Route path="/notes" element={<Notes />} />
    <Route path="/notes/add" element={<AddNotePage />} />
    <Route path="/notes/edit/:id" element={<NotesEditPage />} />
  </Routes>
);

export default AppRoutes;
