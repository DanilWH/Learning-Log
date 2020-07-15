package com.example.LearningLog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.LearningLog.models.Topic;
import com.example.LearningLog.models.User;

public interface CommonOperationsForControllers {
    
    public static void check_topic_owner(Topic topic, User current_user) {
        /*** checks if the current user is the owner of the topic. ***/
        
        String topic_owner = topic.getOwner().getUsername();
        
        if (topic_owner.equals(current_user.getUsername()) != true)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
