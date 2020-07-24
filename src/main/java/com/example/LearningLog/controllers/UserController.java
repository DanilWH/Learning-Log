package com.example.LearningLog.controllers;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.LearningLog.models.Role;
import com.example.LearningLog.models.User;
import com.example.LearningLog.repos.UserRepo;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo;
    
    @GetMapping
    public String users(Map<String, Object> model) {
        model.put("users", this.userRepo.findAll());
        
        return "users_list";
    }
    
    @GetMapping("/{userId}/change")
    public String change_user(
            @PathVariable Long userId,
            Map<String, Object> model
    ) {
        User user = this.userRepo.findById(userId).get();
        
        model.put("user", user);
        model.put("roles", Role.values());
        
        return "change_user";
    }
    
    @PostMapping("/{userId}/change")
    public String update_user(
            @PathVariable Long userId,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Set<Role> roles
    ) {
        User user = this.userRepo.findById(userId).get();
        
        user.setUsername(username);
        user.setPassword(password);
        
        user.setRoles(roles);
        
        this.userRepo.save(user);
        
        return "redirect:/admin/users";
    }

}
