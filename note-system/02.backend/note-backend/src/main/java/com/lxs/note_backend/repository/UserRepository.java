package com.lxs.note_backend.repository;
import com.lxs.note_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);         // 检查是否存在
    Optional<User> findByUsername(String username);    // 获取用户对象
}
