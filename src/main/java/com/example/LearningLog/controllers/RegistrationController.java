package com.example.LearningLog.controllers;

import java.util.Collections;
import java.util.Map;

import com.example.LearningLog.domains.Role;
import com.example.LearningLog.domains.User;
import com.example.LearningLog.repos.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDB = this.userRepo.findByUsername(user.getUsername());

        if (userFromDB != null) {
            model.put("message", "The user already exsists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        this.userRepo.save(user);

        return "redirect:/login";
    }
}
