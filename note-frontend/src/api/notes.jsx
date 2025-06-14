const baseUrl = import.meta.env.VITE_API_BASE_URL;

export const getNotes = async () => {
  const res = await fetch(`${baseUrl}/notes`);
  return res.json();
};

export const createNote = async (note) => {
  const res = await fetch(`${baseUrl}/notes`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(note),
  });
  return res.json();
};
