import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

import {
  Container,
  Typography,
  Button,
  CircularProgress,
  Box,
  Stack,
} from '@mui/material';
import { fetchNotes } from '../api/noteApi';
import { useNavigate } from 'react-router-dom';

export default function NotesPage() {
  const [notes, setNotes] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const user = JSON.parse(localStorage.getItem('user'));
  const userId = user?.id;

  const loadNotes = async () => {
    setLoading(true);
    try {
      const res = await fetchNotes(userId);
        console.log('fetchNotes response:', res.data);
      setNotes(res.data);
    } catch (err) {
      alert('ノートの読み込みに失敗しました');
    } finally {
      setLoading(false);
    }
  };

useEffect(() => {
  console.log('NotesPage mounted, userId:', userId);
  if (userId) {
    loadNotes();
  }
}, []);  // 空依赖数组，组件挂载时执行一次

const location = useLocation();

useEffect(() => {
  if (location.state?.updated) {
    console.log('检测到更新，重新加载笔记...');
    loadNotes();
    // 清除状态，避免刷新后重复加载
    window.history.replaceState({}, '');
  }
}, [location.state]);

  const handleLogout = () => {
    localStorage.removeItem('user');
    navigate('/login');
  };

  if (loading) return <CircularProgress sx={{ mt: 4 }} />;

  return (
    <Container sx={{ mt: 4 }}>
      {/* 顶部标题 + 登出按钮 */}
      <Stack direction="row" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">ノート一覧</Typography>
        <Button variant="outlined" color="error" onClick={handleLogout}>
          ログアウト
        </Button>
      </Stack>

      {notes.length === 0 ? (
        <Box mt={4}>
          <Typography>ノートがありません。追加しますか？</Typography>
          <Button variant="contained" sx={{ mt: 2 }} onClick={() => navigate('/notes/add')}>
            ノートを追加
          </Button>
        </Box>
      ) : (
        <Box mt={2}>
             {notes.map((note) => (
      <Box
        key={note.id}
        mb={2}
        sx={{
          p: 2,
          borderRadius: 2,
                  backgroundColor: '#e3f2fd',
          boxShadow: 1,
          transition: 'box-shadow 0.3s ease, background-color 0.3s ease',
          cursor: 'pointer',
          '&:hover': {
            boxShadow: 6,
            backgroundColor: '#f9f9f9',
          },
        }}
        onClick={() => navigate(`/notes/edit/${note.id}`)}
      >
        <Typography variant="h6" color="primary">
          {note.title}
        </Typography>
      </Box>
          ))}
          <Button variant="contained" sx={{ mt: 2 }} onClick={() => navigate('/notes/add')}>
            新しいノートを追加
          </Button>
        </Box>
      )}
    </Container>
  );
}
