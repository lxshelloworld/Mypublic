import React, { useState } from 'react';
import {
  Container,
  Typography,
  TextField,
  Button,
  Box,
  Snackbar,
  Alert,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { createNote } from '../api/noteApi';

export default function AddNotePage() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async () => {
    if (!title || !content) {
      setError('タイトルと内容は必須です');
      return;
    }

    const user = JSON.parse(localStorage.getItem('user'));
    const userId = user?.id;

    if (!userId) {
      setError('ログイン情報が見つかりません');
      return;
    }

    try {
      const user = JSON.parse(localStorage.getItem('user'));
      await createNote({ title, content, userId ,user: { id: user.id }});
      setSuccess(true);
      setTimeout(() => navigate('/notes', { state: { updated: true } }), 1500); // 成功后 1.5 秒跳转
    } catch (err) {
      setError('ノートの追加に失敗しました');
    }
  };

  return (
    <Container maxWidth="sm" sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        ノートを追加
      </Typography>

      <TextField
        label="タイトル"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        fullWidth
        margin="normal"
      />

      <TextField
        label="内容"
        value={content}
        onChange={(e) => setContent(e.target.value)}
        fullWidth
        multiline
        rows={4}
        margin="normal"
      />

      <Box mt={2}>
        <Button variant="contained" onClick={handleSubmit}>
          保存
        </Button>
        <Button sx={{ ml: 2 }} onClick={() => navigate('/notes')}>
          戻る
        </Button>
      </Box>

      {/* 成功提示 */}
      <Snackbar open={success} autoHideDuration={3000}>
        <Alert severity="success" variant="filled">
          ノートを追加しました！
        </Alert>
      </Snackbar>

      {/* 错误提示 */}
      <Snackbar
        open={!!error}
        autoHideDuration={3000}
        onClose={() => setError('')}
      >
        <Alert severity="error" variant="filled">
          {error}
        </Alert>
      </Snackbar>
    </Container>
  );
}
