import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Box,
  Button,
  Stack,
  IconButton,
  Snackbar,
  Alert,
} from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import AuthForm from '../components/AuthForm';
import { register } from '../api/auth';

const Register = () => {
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    const { username, email, password } = e.target.elements;

    try {
      await register(username.value, email.value, password.value);
      navigate('/login', { state: { registered: true } });
    } catch (err) {
      setErrorMessage(err.message);
    }
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setErrorMessage('');
  };

  return (
    <Container maxWidth="sm">
      <Box mt={4} display="flex" alignItems="center">
        <IconButton onClick={() => navigate(-1)} sx={{ mr: 1 }}>
          <ArrowBackIcon />
        </IconButton>
        <Typography variant="h5">新規登録</Typography>
      </Box>

      <Box mt={4}>
        <AuthForm onSubmit={handleSubmit} isLogin={false} />

        <Stack direction="row" mt={4}>
          <Button variant="contained" fullWidth type="submit" form="auth-form">
            登録
          </Button>
        </Stack>

        <Snackbar
          open={!!errorMessage}
          autoHideDuration={4000}
          onClose={handleClose}
          anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
        >
          <Alert severity="error" onClose={handleClose} variant="filled" elevation={6}>
            {errorMessage}
          </Alert>
        </Snackbar>
      </Box>
    </Container>
  );
};

export default Register;
