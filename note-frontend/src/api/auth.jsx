export const login = async (username, password) => {
  // 替换为实际 API 地址
  const res = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  });
    const data = await res.json();

  if (!res.ok) {
    // ステータスコードが200以外なら例外を投げる
    throw new Error( 'ログイン失敗: ユーザー名またはパスワードが間違っています');
  }

  return data;
};

export const register = async (username, email, password) => {
  const res = await fetch('http://localhost:8080/api/auth/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, email, password }),
  });
   // 取得するレスポンスをテキストではなく JSON で取得
  const data = await res.json();

  if (!res.ok) {
    // ステータスコードが200以外なら例外を投げる
    throw new Error(data.message || '登録エラー');
  }

  return data;
};
