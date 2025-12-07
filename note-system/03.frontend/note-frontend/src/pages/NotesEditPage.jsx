import React, { useEffect, useState } from 'react';
import {
  Container,
  TextField,
  Button,
  Typography,
  Stack,
  Box,
} from '@mui/material';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchNoteById, deleteNote, updateNote } from '../api/noteApi';

export default function NotesEditPage() {
  const { id } = useParams(); // 获取 URL 中的笔记 ID
  const navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [loading, setLoading] = useState(true);

  // 加载笔记详情
  useEffect(() => {
    const loadNote = async () => {
      try {
        const res = await fetchNoteById(id);
        const note = res.data;
        setTitle(note.title);
        setContent(note.content);
      } catch (err) {
        alert('ノートの読み込みに失敗しました');
        navigate('/notes');
      } finally {
        setLoading(false);
      }
    };
    loadNote();
  }, [id, navigate]);

// 保存更新
const handleSave = async () => {
  try {
    const user = JSON.parse(localStorage.getItem('user'));
    await updateNote(id, {
      title,
      content,
      user: { id: user.id }, // ⭐ 添加 user.id，确保更新后笔记仍然归属当前用户
    });
    alert('ノートを更新しました');
    navigate('/notes', { state: { updated: true } });
  } catch (err) {
    alert('更新に失敗しました');
  }
};

  // 删除笔记
  const handleDelete = async () => {
    if (window.confirm('このノートを削除しますか？')) {
      try {
        await deleteNote(id);
        alert('ノートを削除しました');
        navigate('/notes',{ state: { updated: true } });
      } catch (err) {
        alert('削除に失敗しました');
      }
    }
  };

  if (loading) {
    return (
      <Container sx={{ mt: 4 }}>
        <Typography>読み込み中...</Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="sm" sx={{ mt: 4 }}>
      <Stack spacing={2}>
        <Typography variant="h5">ノートを編集</Typography>

        <TextField
          label="タイトル"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          fullWidth
        />

        <TextField
          label="内容"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          multiline
          rows={6}
          fullWidth
        />

        <Stack direction="row" spacing={2}>
          <Button variant="outlined" onClick={() => navigate('/notes')}>
            戻る
          </Button>
          <Button variant="contained" color="primary" onClick={handleSave}>
            保存
          </Button>
          <Button variant="outlined" color="error" onClick={handleDelete}>
            削除
          </Button>
        </Stack>
      </Stack>
    </Container>
  );
}
