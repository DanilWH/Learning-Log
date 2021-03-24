package com.example.LearningLog.controllers;

import com.example.LearningLog.models.Accesses;
import com.example.LearningLog.models.Entry;
import com.example.LearningLog.models.Topic;
import com.example.LearningLog.models.User;
import com.example.LearningLog.repos.EntryRepo;
import com.example.LearningLog.repos.TopicRepo;
import com.example.LearningLog.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class TopicController {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private EntryRepo entryRepo;
    
    @GetMapping(value={"/user_topics/{userId}", "/public_topics"})
    public String my_topics(
            @AuthenticationPrincipal User currentUser,
            @PathVariable(required = false) Long userId,
            Map<String, Object> model,
            HttpServletRequest request
    ) {
        Iterable<Topic> topics = null;

        if (request.getRequestURI().equals("/public_topics")) {
            topics = this.topicRepo.findByAccessOrderByTimeCreationDesc(Accesses.PUBLIC);
        }
        else if (request.getRequestURI().equals("/user_topics/" + userId)) {
            User user = this.userRepo.findById(userId).orElseThrow(NoResultException::new);
            topics = this.topicRepo.findByOwnerIdOrderByTimeCreationDesc(userId);

            model.put("user", user);
        }

        model.put("topics", topics);
        model.put("isCurrentUser", userId != null && currentUser.getId().equals(userId));

        return "topics";
    }
    
    @GetMapping("/new_topic")
    public String new_topic(Map<String, Object> model) {
        model.put("accesses", Accesses.values());
        
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
            model.put("message", CommonOperationsForControllers.fieldRequired);
            return new_topic(model);
        }
            
        if (description != null && description.trim().isEmpty())
            description = title;
        
        Topic new_topic = new Topic(title, description, user, access);
        Topic saved_topic = this.topicRepo.save(new_topic);
        
        return "redirect:/topic/" + saved_topic.getId() + "/entries";
    }
    
    @GetMapping("/edit_topic/{topicId}")
    public String edit_topic(
            @PathVariable Long topicId,
            @AuthenticationPrincipal User current_user,
            Map<String, Object> model
    ) {
        Topic topic = this.topicRepo.findById(topicId).get();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);
        
        model.put("topic", topic);
        model.put("accesses", Accesses.values());
        
        return "edit_topic";
    }
    
    @PostMapping("/edit_topic/{topicId}")
    public String update_topic(
            @AuthenticationPrincipal User current_user,
            @PathVariable Long topicId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Accesses access
    ) {
        Topic topic = this.topicRepo.findById(topicId).get();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);
        
        topic.setTitle(title);
        topic.setDescription(description);
        topic.setAccess(access);
        
        this.topicRepo.save(topic);
        
        return "redirect:/topic/" + topicId + "/entries";
    }
    
    @GetMapping("/delete_topic/{topicId}")
    public String delete_topic_confirmation(
            @PathVariable Long topicId,
            @AuthenticationPrincipal User current_user,
            Map<String, Object> model
    ) {
        Topic topic = this.topicRepo.findById(topicId).get();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);
        
        model.put("topic", topic);
        
        return "delete_topic";
    }
    
    @PostMapping("/delete_topic/{topicId}")
    public String delete_topic(
            @PathVariable Long topicId,
            @AuthenticationPrincipal User current_user
    ) {
        Topic topic = this.topicRepo.findById(topicId).get();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);

        // delete all files related to the topic from the server.
        Iterable<Entry> entries = this.entryRepo.findByTopicId(topicId);
        for (Entry entry : entries)
            this.entryRepo.delete(entry);

        this.topicRepo.delete(topic);
        
        return "redirect:/my_topics";
    }
}
