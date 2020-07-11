package com.example.LearningLog.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.LearningLog.models.Role;
import com.example.LearningLog.models.User;
import com.example.LearningLog.repos.UserRepo;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            User user, @RequestParam String repeat_password,
            Map<String, Object> model
        ) {
        User userFromDB = this.userRepo.findByUsername(user.getUsername());

        if (userFromDB != null)
            model.put("message", "The user already exsists!");
        
        if (user.getPassword().equals(repeat_password) == false)
            model.put("message", "Your passwords didn't match. Please try again.");

        if (model.containsKey("message")) return "registration";
        
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        this.userRepo.save(user);

        return "redirect:/login";
    }
}
