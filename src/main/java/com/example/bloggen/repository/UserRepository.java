package com.example.bloggen.repository;

import com.example.bloggen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
