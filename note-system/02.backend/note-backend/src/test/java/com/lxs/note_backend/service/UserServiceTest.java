package com.lxs.note_backend.service;

import com.lxs.note_backend.entity.User;
import com.lxs.note_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService();
        // 使用反射注入 repository（或用构造函数更好）
        userService = new UserService() {{
            // 手动注入模拟的 Repository
            try {
                var field = UserService.class.getDeclaredField("userRepository");
                field.setAccessible(true);
                field.set(this, userRepository);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }};
    }

    @Test
    public void testRegister_NewUser_Success() {
        User user = new User("testuser1", "123456", "test1@example.com");

        when(userRepository.existsByUsername("testuser1")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = userService.register(user);
        assertEquals("注册成功", result);
    }

    @Test
    public void testRegister_ExistingUsername() {
        User user = new User("testuser", "123456", "test@example.com");

        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        String result = userService.register(user);
        assertEquals("用户名已存在", result);
    }
}
