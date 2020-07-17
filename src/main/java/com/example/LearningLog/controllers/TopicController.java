package com.example.LearningLog.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.LearningLog.models.Topic;
import com.example.LearningLog.models.User;
import com.example.LearningLog.repos.TopicRepo;

@Controller
public class TopicController {
    @Autowired
    private TopicRepo topicRepo;
    
    @GetMapping("/my_topics")
    public String topics(@AuthenticationPrincipal User user, Map<String, Object> model) {
        Iterable<Topic> topics = this.topicRepo.findByOwnerId(user.getId());
        model.put("topics", topics);
        
        return "topics";
    }
    
    @GetMapping("/new_topic")
    public String new_topic() {
        return "new_topic";
    }
    
    @PostMapping("/new_topic")
    public String add_new_topic(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            Map<String, Object> model
    ) {
        Topic new_topic = new Topic(title, user);
        Topic saved_topic = this.topicRepo.save(new_topic);
        
        return "redirect:/topic/" + saved_topic.getId() + "/entries";
    }
}
