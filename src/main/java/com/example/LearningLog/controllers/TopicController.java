package com.example.LearningLog.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.LearningLog.models.Accesses;
import com.example.LearningLog.models.Topic;
import com.example.LearningLog.models.User;
import com.example.LearningLog.repos.TopicRepo;

@Controller
public class TopicController {
    @Autowired
    private TopicRepo topicRepo;
    
    @GetMapping("/my_topics")
    public String my_topics(@AuthenticationPrincipal User user, Map<String, Object> model) {
        Iterable<Topic> topics = this.topicRepo.findByOwnerId(user.getId());
        model.put("topics", topics);
        
        return "topics";
    }
    
    @GetMapping("/public_topics")
    public String public_topics(Map<String, Object> model) {
        Iterable<Topic> topics = this.topicRepo.findByAccess(Accesses.PUBLIC);
        model.put("topics", topics);
        
        return "topics";
    }
    
    @GetMapping("/new_topic")
    public String new_topic(Map<String, Object> model) {
        model.put("PRIVATE", Accesses.PRIVATE);
        model.put("PUBLIC", Accesses.PUBLIC);
        
        return "new_topic";
    }
    
    @PostMapping("/new_topic")
    public String add_new_topic(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Accesses access,
            Map<String, Object> model
    ) {
        if (title != null && title.trim().isEmpty()) {
            model.put("message", "Title can not contain white spaces only!");
            return new_topic(model);
        }
            
        if (description != null && description.trim().isEmpty())
            description = title;
        
        Topic new_topic = new Topic(title, description, user, access);
        Topic saved_topic = this.topicRepo.save(new_topic);
        
        return "redirect:/topic/" + saved_topic.getId() + "/entries";
    }
}
