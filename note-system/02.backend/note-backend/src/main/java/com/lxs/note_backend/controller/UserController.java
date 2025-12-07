package com.lxs.note_backend.controller;


import com.lxs.note_backend.entity.User;
import com.lxs.note_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        String result = userService.register(user);
        Map<String, String> res = new HashMap<>();
        if ("用户名已存在".equals(result)) {
            res.put("message", "ユーザー名は既に存在します");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        } else {
            res.put("message", "登録成功");
            return ResponseEntity.ok(res);
        }
    }

}
