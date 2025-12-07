import React from 'react';
import { TextField, Button, Stack } from '@mui/material';

const AuthForm = ({ onSubmit, isLogin }) => {
  return (
    <form id="auth-form" onSubmit={onSubmit}>
      <Stack spacing={2}>
        <TextField label="ユーザー名" name="username" type="username" fullWidth required />
        {!isLogin && (
          <TextField label="メールアドレス" name="email" fullWidth required />
        )}
        
        <TextField label="パスワード" name="password" type="password" fullWidth required />
      </Stack>
    </form>
  );
};

export default AuthForm;
