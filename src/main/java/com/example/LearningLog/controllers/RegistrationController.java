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
            User user, @RequestParam String password_confirm,
            Map<String, Object> model
    ) {
        String username = user.getUsername();
        String password = user.getPassword();
        
        String username_message = CommonOperationsForControllers.isUsernameValid(username, this.userRepo);
        if (username_message != null) {
            model.put("username_message", username_message);
            return "registration";
        }
        
        String password_message = CommonOperationsForControllers.isPasswordValid(password, password_confirm);
        if (password_message != null) {
            model.put("password_message", password_message);
            return "registration";
        }
        
        // saving the user into the database.
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        this.userRepo.save(user);

        return "redirect:/login";
    }
}
