import React, { useEffect, useState } from 'react';
import { getNotes } from '../api/notes';
import { Container, Typography } from '@mui/material';

const Notes = () => {
  const [notes, setNotes] = useState([]);

  useEffect(() => {
    getNotes().then(setNotes);
  }, []);

  return (
    <Container>
<Typography variant="h4" mt={4}>ノート一覧</Typography>

{notes.length === 0 ? (
  <Typography mt={2}>ノートがありません</Typography>
) : (
  notes.map((note) => <Typography key={note.id}>{note.title}</Typography>)
)}
    </Container>
  );
};

export default Notes;
