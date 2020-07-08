package com.example.LearningLog.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.LearningLog.models.Entry;
import com.example.LearningLog.models.Topic;
import com.example.LearningLog.repos.EntryRepo;
import com.example.LearningLog.repos.TopicRepo;

@Controller
public class EntryController {
    
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private EntryRepo entryRepo;

    @GetMapping("/topics/{topicId}/entries")
    public String entries(
            @PathVariable(value="topicId") Integer topicId, Map<String, Object> model
    ) {
        Topic topic = this.topicRepo.findById(topicId).get();
        model.put("topic", topic);
        
        Iterable<Entry> entries = this.entryRepo.findByTopicId(topicId);
        model.put("entries", entries);
        
        return "entries";
    }
    
    @GetMapping("/topics/{topicId}/entries/new_entry")
    public String new_entry(
            @PathVariable(value="topicId") Integer topicId, Map<String, Object> model
    ) {
        Topic topic = this.topicRepo.findById(topicId).get();
        model.put("topic", topic);
        
        return "new_entry";
    }
    
    @PostMapping("/topics/{topicId}/entries/new_entry")
    public String addEntry(
            @PathVariable(value="topicId") Integer topicId,
            @RequestParam String text,
            Map<String, Object> model
    ) {
        Topic topic = this.topicRepo.findById(topicId).get();
        model.put("topic", topic);
        
        Entry new_entry = new Entry(text, topic);
        this.entryRepo.save(new_entry);
        
        return "redirect:/topics/" + topicId + "/entries";
        
        /**
         * More modern approach:
         * 
         * add the parameter "@Valid Entry entry"
         * 
         * this.topicRepo.findById(topicId).map(topic -> {
         *     entry.setTopic(topic);
         *     return this.entryRepo.save(entry);
         * });
         * 
         * return "redirect:/topics/" + topicId + "/entries";
         */
    }
}
