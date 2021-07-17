package com.example.LearningLog.controllers;

import com.example.LearningLog.models.Accesses;
import com.example.LearningLog.models.Entry;
import com.example.LearningLog.models.Topic;
import com.example.LearningLog.models.User;
import com.example.LearningLog.repos.EntryRepo;
import com.example.LearningLog.repos.TopicRepo;
import com.example.LearningLog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class TopicController {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private EntryRepo entryRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/public_topics")
    public String public_topics(Model model) {
        Iterable<Topic> topics = this.topicRepo.findByAccessOrderByTimeCreationDesc(Accesses.PUBLIC);

        model.addAttribute("topics", topics);

        return "topics";
    }

    @GetMapping("/user_topics/{userId}")
    public String user_topics(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long userId,
            Model model
    ) {
        Iterable<Topic> topics = null;

        User user = this.userService.getUserById(userId);
        topics = this.topicRepo.findByOwnerIdOrderByTimeCreationDesc(userId);

        model.addAttribute("user", user);
        model.addAttribute("topics", topics);
        model.addAttribute("isCurrentUser", userId != null && currentUser.getId().equals(userId));
        model.addAttribute("isSubscribed", user.getSubscribers().contains(currentUser));
        System.out.println(user.getSubscribers().contains(currentUser));
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());

        return "userTopics";
    }

    @GetMapping("/user_topics/{userId}/{type}")
    public String userSubscribe(
            @PathVariable Long userId,
            @PathVariable String type,
            @AuthenticationPrincipal User currentUser
    ) {
        if (type.equals("subscribe")) {
            this.userService.subscribe(userId, currentUser);
        } else if (type.equals("unsubscribe")) {
            this.userService.unsubscribe(userId, currentUser);
        }

        return "redirect:/user_topics/" + userId;
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
