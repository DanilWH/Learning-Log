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

    @GetMapping("/topic/{topicId}/entries")
    public String entries(
            @PathVariable(value="topicId") Integer topicId,
            @AuthenticationPrincipal User current_user,
            Map<String, Object> model
    ) {
        /*** Shows a list of the user's entries that are associated with a certain topic. ***/
        
        Topic topic = this.topicRepo.findById(topicId).get();
        
        CommonOperationsForControllers.checkTopicAccessabilityAndTopicOwner(topic, current_user);
        
        model.put("topic", topic);
        
        Iterable<Entry> entries = this.entryRepo.findByTopicIdOrderByDateTime(topicId);
        model.put("entries", entries);
        
        return "entries";
    }
    
    @GetMapping("/topic/{topicId}/entries/new_entry")
    public String new_entry(
            @PathVariable(value="topicId") Integer topicId,
            @AuthenticationPrincipal User current_user,
            Map<String, Object> model
    ) {
        /*** 
         * Renders the "New entry" page where the user
         * can insert a name of the new topic and add it.
        ***/
        
        Topic topic = this.topicRepo.findById(topicId).get();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);
        
        model.put("topic", topic);
        
        return "new_entry";
    }
    
    @PostMapping("/topic/{topicId}/entries/new_entry")
    public String add_entry(
            @PathVariable(value="topicId") Integer topicId,
            @AuthenticationPrincipal User current_user,
            @RequestParam String text,
            Map<String, Object> model
    ) {
        /*** Processes adding a new entry to the database. ***/
        
        Topic topic = this.topicRepo.findById(topicId).get();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);
        
        model.put("topic", topic);
        
        if (text != null && text.trim().isEmpty()) {
            model.put("message", CommonOperationsForControllers.fieldRequired);
            // the 3 lines of code that are above the if statement are the same
            // as in the add_entry method, we don't want to execute the same algorithm again,
            // so we put the if statement after these 3 lines of code adding "message"
            // to the model if the input is incorrect and then just call the new_entry template.
            return "new_entry";
        }
        
        // create the new entry and save it into the database.
        Entry new_entry = new Entry(text, topic);
        this.entryRepo.save(new_entry);
        
        return "redirect:/topic/" + topicId + "/entries";
        
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
    
    @GetMapping("/topic/{topicId}/entries/edit_entry/{entryId}")
    public String edit_entry(
            @PathVariable(value="entryId") Long entryId,
            @AuthenticationPrincipal User current_user,
            Map<String, Object> model
    ) {
        /*** Renders the "Edit entry" page where user can edit their entries. ***/
        
        Entry entry = this.entryRepo.findById(entryId).get();
        Topic topic = entry.getTopic();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);
        
        model.put("topic", topic);
        model.put("entry", entry);
        
        return "edit_entry";
    }
    
    @PostMapping("/topic/{topicId}/entries/edit_entry/{entryId}")
    public String update_entry(
            @PathVariable(value="entryId") Long entryId,
            @AuthenticationPrincipal User current_user,
            @RequestParam String text
    ) {
        /*** Processes editing a entry and saves the changes in the database. ***/
        
        Entry entry = this.entryRepo.findById(entryId).get();
        Topic topic = entry.getTopic();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);
        
        entry.setText(text);
        this.entryRepo.save(entry);
        
        return "redirect:/topic/" + topic.getId() + "/entries";
    }
    
    @GetMapping("/topic/{topicId}/entries/delete_entry/{entryId}")
    public String delete_entry_confirmation(
            @PathVariable(value="entryId") Long entryId,
            @AuthenticationPrincipal User current_user,
            Map<String, Object> model
    ) {
        /*** 
         * Renders the "Delete entry" page where users are
         * asked if the really want to delete the entry.
        ***/
        
        Entry entry = this.entryRepo.findById(entryId).get();
        Topic topic = entry.getTopic();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);
        
        model.put("topic", topic);
        model.put("entry", entry);
        
        return "delete";
    }
    
    @PostMapping("/topic/{topicId}/entries/delete_entry/{entryId}")
    public String delete_entry(
            @PathVariable(value="entryId") Long entryId,
            @AuthenticationPrincipal User current_user
    ) {
        /*** Processes deleting a entry from the database. ***/
        
        Entry entry = this.entryRepo.findById(entryId).get();
        Topic topic = entry.getTopic();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);
        
        this.entryRepo.delete(entry);
        
        return "redirect:/topic/" + topic.getId() + "/entries";
    }
}