package com.example.LearningLog.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.LearningLog.models.Entry;
import com.example.LearningLog.models.Topic;
import com.example.LearningLog.repos.EntryRepo;
import com.example.LearningLog.repos.TopicRepo;

@Controller
public class EntryController {
    
    @Autowired
    private EntryRepo entryRepo;
    @Autowired
    private TopicRepo topicRepo;

    @GetMapping("/topics/{topicId}/entries")
    public String entries(
            @PathVariable(value="topicId") Integer topicId, Map<String, Object> model
        ) {
        Optional<Topic> topic = this.topicRepo.findById(topicId);
        model.put("topicTitle", topic.get().getTitle());
        
        Iterable<Entry> entries = this.entryRepo.findByTopicId(topicId);
        model.put("entries", entries);
        
        return "entries";
    }
}
