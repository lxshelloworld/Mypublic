import { Card, CardContent, Typography, IconButton } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

export default function NoteCard({ note, onDelete }) {
  return (
    <Card sx={{ marginBottom: 2 }}>
      <CardContent>
        <Typography variant="h6">{note.title}</Typography>
        <Typography variant="body2" color="text.secondary">
          {note.content}
        </Typography>
        <IconButton onClick={() => onDelete(note.id)} color="error">
          <DeleteIcon />
        </IconButton>
      </CardContent>
    </Card>
  );
}
