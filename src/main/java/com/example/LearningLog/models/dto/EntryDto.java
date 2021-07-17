package com.example.LearningLog.models.dto;

import com.example.LearningLog.models.Entry;
import com.example.LearningLog.models.Topic;
import com.example.LearningLog.models.Upload;
import com.example.LearningLog.models.User;
import com.example.LearningLog.models.util.EntryHelper;

import java.time.LocalDateTime;
import java.util.List;

public class EntryDto {
    private final Long id;
    private final LocalDateTime dateTime;
    private final String text;
    private final Topic topic;
    private final List<Upload> uploads;
    private final Long likes;
    private final Boolean meLiked;

    public EntryDto(Entry entry, User currentUser) {
        this.id = entry.getId();
        this.dateTime = entry.getDateTime();
        this.text = entry.getText();
        this.topic = entry.getTopic();
        this.uploads = entry.getUploads();

        this.likes = (long) entry.getLikes().size();
        this.meLiked = entry.getLikes().contains(currentUser);
    }

    public String getDateTimeFormatted() {
        return EntryHelper.formatDateTime(this.dateTime);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getText() {
        return text;
    }

    public Topic getTopic() {
        return topic;
    }

    public List<Upload> getUploads() {
        return uploads;
    }

    public Long getLikes() {
        return likes;
    }

    public Boolean getMeLiked() {
        return meLiked;
    }
}
