package com.example.LearningLog.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.LearningLog.models.Entry;
import com.example.LearningLog.models.Topic;
import com.example.LearningLog.models.User;
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
            @PathVariable(value="topicId") Integer topicId,
            @AuthenticationPrincipal User current_user,
            Map<String, Object> model
    ) {
        Topic topic = this.topicRepo.findById(topicId).get();
        
        CommonOperationsForControllers.check_topic_owner(topic, current_user);
        
        model.put("topic", topic);
        
        Iterable<Entry> entries = this.entryRepo.findByTopicIdOrderByDateTime(topicId);
        model.put("entries", entries);
        
        return "entries";
    }
    
    @GetMapping("/topics/{topicId}/entries/new_entry")
    public String new_entry(
            @PathVariable(value="topicId") Integer topicId,
            @AuthenticationPrincipal User current_user,
            Map<String, Object> model
    ) {
        Topic topic = this.topicRepo.findById(topicId).get();
        
        CommonOperationsForControllers.check_topic_owner(topic, current_user);
        
        model.put("topic", topic);
        
        return "new_entry";
    }
    
    @PostMapping("/topics/{topicId}/entries/new_entry")
    public String add_entry(
            @PathVariable(value="topicId") Integer topicId,
            @AuthenticationPrincipal User current_user,
            @RequestParam String text,
            Map<String, Object> model
    ) {
        Topic topic = this.topicRepo.findById(topicId).get();
        
        CommonOperationsForControllers.check_topic_owner(topic, current_user);
        
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
    
    @GetMapping("/topics/{topicId}/entries/edit_entry/{entryId}")
    public String edit_entry(
            @PathVariable(value="entryId") Long entryId,
            @AuthenticationPrincipal User current_user,
            Map<String, Object> model
    ) {
        Entry entry = this.entryRepo.findById(entryId).get();
        Topic topic = entry.getTopic();
        
        CommonOperationsForControllers.check_topic_owner(topic, current_user);
        
        model.put("topic", topic);
        model.put("entry", entry);
        
        return "edit_entry";
    }
    
    @PostMapping("/topics/{topicId}/entries/edit_entry/{entryId}")
    public String update_entry(
            @PathVariable(value="entryId") Long entryId,
            @AuthenticationPrincipal User current_user,
            @RequestParam String text
    ) {
        Entry entry = this.entryRepo.findById(entryId).get();
        Topic topic = entry.getTopic();
        
        CommonOperationsForControllers.check_topic_owner(topic, current_user);
        
        entry.setText(text);
        this.entryRepo.save(entry);
        
        return "redirect:/topics/" + topic.getId() + "/entries";
    }
    
    @GetMapping("/topics/{topicId}/entries/delete_entry/{entryId}")
    public String delete_entry_confirmation(
            @PathVariable(value="entryId") Long entryId,
            @AuthenticationPrincipal User current_user,
            Map<String, Object> model
    ) {
        Entry entry = this.entryRepo.findById(entryId).get();
        Topic topic = entry.getTopic();
        
        CommonOperationsForControllers.check_topic_owner(topic, current_user);
        
        model.put("topic", topic);
        model.put("entry", entry);
        
        return "delete";
    }
    
    @PostMapping("/topics/{topicId}/entries/delete_entry/{entryId}")
    public String delete_entry(
            @PathVariable(value="entryId") Long entryId,
            @AuthenticationPrincipal User current_user
    ) {
        Entry entry = this.entryRepo.findById(entryId).get();
        Topic topic = entry.getTopic();
        
        CommonOperationsForControllers.check_topic_owner(topic, current_user);
        
        this.entryRepo.delete(entry);
        
        return "redirect:/topics/" + topic.getId() + "/entries";
    }
}