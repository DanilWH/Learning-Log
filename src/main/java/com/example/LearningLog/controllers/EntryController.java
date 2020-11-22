package com.example.LearningLog.controllers;

import com.example.LearningLog.models.Entry;
import com.example.LearningLog.models.Topic;
import com.example.LearningLog.models.Upload;
import com.example.LearningLog.models.User;
import com.example.LearningLog.repos.EntryRepo;
import com.example.LearningLog.repos.TopicRepo;
import com.example.LearningLog.repos.UploadRepo;
import com.example.LearningLog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/topic")
public class EntryController {
    
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private EntryRepo entryRepo;
    @Autowired
    private UploadRepo uploadRepo;

    @Autowired
    private UploadService uploadService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/{topicId}/entries")
    public String entries(
            @PathVariable(value="topicId") Long topicId,
            @AuthenticationPrincipal User current_user,
            HttpServletResponse response,
            HttpServletRequest request,
            Map<String, Object> model
    ) {
        /*** Shows a list of the user's entries that are associated with a certain topic. ***/
        
        Topic topic = this.topicRepo.findById(topicId).orElseThrow(() -> new NoResultException());
        
        CommonOperationsForControllers.checkTopicAccessabilityAndTopicOwner(topic, current_user);
        
        model.put("topic", topic);

        List<Entry> entries = this.entryRepo.findByTopicIdOrderByDateTimeDesc(topicId);

        // put the entries into the model.
        model.put("entries", entries);

        return "entries";
    }

    @GetMapping("/images")
    public void showImages(
            @RequestParam("id") Long imageId,
            HttpServletResponse response
    ) throws IOException {
        // find the necessary image.
        Upload upload = this.uploadRepo.findById(imageId).orElseThrow(() -> new NoResultException());

        // write the bytes of the uploads to the response.
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(upload.getBytes());

        // close the response connection.
        response.getOutputStream().close();
    }
    
    @GetMapping("/{topicId}/entries/new_entry")
    public String new_entry(
            @PathVariable(value="topicId") Long topicId,
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
    
    @PostMapping("/{topicId}/entries/new_entry")
    public String add_entry(
            @PathVariable(value="topicId") Long topicId,
            @AuthenticationPrincipal User current_user,
            @RequestParam String text,
            @RequestParam List<MultipartFile> files,
            Map<String, Object> model
    ) throws IOException {
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
        
        // create the new entry object.
        Entry new_entry = new Entry(text, topic);
        // upload images if any.
        new_entry.setUploads(files.stream().map(file -> {
            try {
                return this.uploadService.createUploadObject(file);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList()));

        // save the entry into the database.
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
    
    @GetMapping("/{topicId}/entries/edit_entry/{entryId}")
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
    
    @PostMapping("/{topicId}/entries/edit_entry/{entryId}")
    public String update_entry(
            @PathVariable(value="entryId") Long entryId,
            @AuthenticationPrincipal User current_user,
            @RequestParam List<MultipartFile> files,
            @RequestParam String text,
            @RequestParam Optional<String[]> onDelete
    ) throws IOException {
        /*** Processes editing a entry and saves the changes in the database. ***/
        
        Entry entry = this.entryRepo.findById(entryId).orElseThrow(() -> new NoResultException());
        Topic topic = entry.getTopic();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);

        // remove the necessary files.
        if (onDelete.isPresent()) {
            //CommonOperationsForControllers.deleteFilesFromServerIfExist(entry, onDelete.get(), this.uploadPath);
            for (String deletingFilename : onDelete.get())
                entry.getUploads().remove(this.uploadRepo.findByFilename(deletingFilename));
        }
        // upload new files.
        for (MultipartFile file : files)
            entry.getUploads().add(this.uploadService.createUploadObject(file));

        entry.setText(text);
        this.entryRepo.save(entry);
        
        return "redirect:/topic/" + topic.getId() + "/entries";
    }
    
    @GetMapping("/{topicId}/entries/delete_entry/{entryId}")
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
        
        return "delete_entry";
    }
    
    @PostMapping("/{topicId}/entries/delete_entry/{entryId}")
    public String delete_entry(
            @PathVariable(value="entryId") Long entryId,
            @AuthenticationPrincipal User current_user
    ) {
        /*** Processes deleting a entry from the database. ***/
        
        Entry entry = this.entryRepo.findById(entryId).get();
        Topic topic = entry.getTopic();
        
        CommonOperationsForControllers.checkTopicOwner(topic, current_user);
        
        // CommonOperationsForControllers.deleteFilesFromServerIfExist(entry, this.uploadPath);
        
        this.entryRepo.delete(entry);
        
        return "redirect:/topic/" + topic.getId() + "/entries";
    }
}