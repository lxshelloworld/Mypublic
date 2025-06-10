import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Container, Typography, Box, Button, Stack, Snackbar,Alert } from '@mui/material';
import MuiAlert from '@mui/material/Alert';
import AuthForm from '../components/AuthForm';
import { login } from '../api/auth';

const Login = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [openSnackbar, setOpenSnackbar] = useState(false);
const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    if (location.state?.registered) {
      setOpenSnackbar(true);
      // 清空状态，防止刷新后再次弹窗
      window.history.replaceState({}, '');
    }
  }, [location.state]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const { username, password } = e.target.elements;
    try{
    await login(username.value, password.value);
    navigate('/notes');
    }catch(err){
      setErrorMessage(err.message);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box mt={8}>
        <Typography variant="h4" gutterBottom>
          ログイン
        </Typography>

        <AuthForm onSubmit={handleSubmit} isLogin />

        {/* 下方按钮区域 */}
        <Stack direction="row" spacing={2} mt={4} justifyContent="space-between">
          <Button
            variant="outlined"
            fullWidth
            onClick={() => navigate('/register')}
          >
            新規登録
          </Button>
          <Button
            variant="contained"
            fullWidth
            type="submit"
            form="auth-form"
          >
            ログイン
          </Button>
        </Stack>

{/* 注册成功提示 */}
<Snackbar
  open={openSnackbar}
  autoHideDuration={4000}
  onClose={() => setOpenSnackbar(false)}
>
  <MuiAlert severity="success" variant="filled" elevation={6}>
    登録が完了しました。ログインしてください。
  </MuiAlert>
</Snackbar>

{/* 登录失败提示 👇添加在这里 */}
<Snackbar
  open={!!errorMessage}
  autoHideDuration={4000}
  onClose={() => setErrorMessage('')}
>
  <Alert severity="error" variant="filled" elevation={6}>
    {errorMessage}
  </Alert>
</Snackbar>
        
      </Box>
    </Container>
  );
};

export default Login;
