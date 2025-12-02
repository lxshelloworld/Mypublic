package com.lxs.note_backend.repository;

import com.lxs.note_backend.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User("testuser", "123456", "test@example.com");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("testuser");
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("testuser", found.get().getUsername());
    }
}
