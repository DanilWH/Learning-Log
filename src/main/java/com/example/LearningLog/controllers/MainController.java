package com.example.LearningLog.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.LearningLog.domains.Topic;
import com.example.LearningLog.repos.TopicRepo;

@Controller
public class MainController {
    @Autowired
    private TopicRepo topicRepo;
    
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/topics")
    public String topics(Map<String, Object> model) {
        Iterable<Topic> topics = this.topicRepo.findAll();
        model.put("topics", topics);
        
        return "topics";
    }
    
    @GetMapping("/new_topic")
    public String new_topic() {
        return "new_topic";
    }
    
    @PostMapping("/new_topic")
    public String add_new_topic(@RequestParam String title, Map<String, Object> model) {
        Topic new_topic = new Topic(title);
        this.topicRepo.save(new_topic);
        
        return "redirect:/topics";
    }
}