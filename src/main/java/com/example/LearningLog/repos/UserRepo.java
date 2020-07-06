package com.example.LearningLog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LearningLog.domains.User;

public interface UserRepo extends JpaRepository<User, Long> {
    
    User findByUsername(String username);

}
