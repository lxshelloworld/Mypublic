package com.lxs.note_backend.service;

import com.lxs.note_backend.entity.User;
import com.lxs.note_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "用户名已存在";
        }
        userRepository.save(user);
        return "注册成功";
    }
}
