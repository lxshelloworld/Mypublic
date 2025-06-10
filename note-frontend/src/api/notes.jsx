export const getNotes = async () => {
  const res = await fetch('/api/notes');
  return res.json();
};

export const createNote = async (note) => {
  const res = await fetch('/api/notes', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(note),
  });
  return res.json();
};
