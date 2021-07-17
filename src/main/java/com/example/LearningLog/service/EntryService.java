package com.example.LearningLog.service;

import com.example.LearningLog.models.Entry;
import com.example.LearningLog.models.Topic;
import com.example.LearningLog.models.User;
import com.example.LearningLog.models.dto.EntryDto;
import com.example.LearningLog.repos.EntryRepo;
import com.example.LearningLog.repos.UploadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntryService {
    @Autowired
    private EntryRepo entryRepo;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UploadRepo uploadRepo;

    @Autowired
    private EntityManager em;

    public Entry getById(Long id) {
        return this.entryRepo.findById(id).orElseThrow(NoResultException::new);
    }

    public List<EntryDto> getByTopicIdOrderByDateTimeDesc(Long topicId, User currentUser) {
        return this.entryRepo.findByTopicIdOrderByDateTimeDesc(topicId)
                .stream()
                .map(entry -> new EntryDto(entry, currentUser))
                .collect(Collectors.toList());
    }

    /**
     * @return Saved entry in the database.
     */
    public Entry addEntry(String text, Topic topic, List<MultipartFile> files) {
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

        // save the entry into the database.
        return this.entryRepo.save(new_entry);
    }

    /**
     * @return Updated entry in the database.
     */
    public Entry updateEntry(
            Entry entry,
            Optional<String[]> onDelete,
            List<MultipartFile> files,
            String text
    ) throws IOException {
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

        return this.entryRepo.save(entry);
    }

    public void delete(Entry entry) {
        this.entryRepo.delete(entry);
    }
}
