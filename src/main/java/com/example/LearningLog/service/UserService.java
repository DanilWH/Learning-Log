package com.example.LearningLog.service;

import com.example.LearningLog.models.User;
import com.example.LearningLog.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepo userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepo.findByUsername(username);
    }

    public void subscribe(Long userId, User currentUser) {
        User user = this.getUserById(userId);
        user.getSubscribers().add(currentUser);

        this.userRepo.save(user);
    }

    public void unsubscribe(Long userId, User currentUser) {
        User user = this.getUserById(userId);
        user.getSubscribers().remove(currentUser);

        this.userRepo.save(user);
    }

    public User getUserById(Long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(NoResultException::new);

        return user;
    }
}
