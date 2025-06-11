import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api', // 根据你的 nginx / proxy 配置修改
  withCredentials: true, // 如果你用了 cookie 登录认证
});

export const fetchNotes = (userId) => api.get(`/notes/user/${userId}`);
export const createNote = (note) => api.post('/notes', note);
export const deleteNote = (id) => api.delete(`/notes/${id}`);
export const fetchNoteById = (id) => api.get(`/notes/${id}`);
export const updateNote = (id, note) => api.put(`/notes/${id}`, note);

