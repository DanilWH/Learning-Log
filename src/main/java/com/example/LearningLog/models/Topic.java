package com.example.LearningLog.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import com.example.LearningLog.controllers.CommonOperationsForControllers;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Size(max = 200)
    private String title;
    
    @Lob
    @Type(type = "text")
    private String description;
    
    // determines if the topic is PRIVATE of PUBLIC.
    private Accesses access;
    
    // keeps the number of entries that belong the topic.
    private Long entriesNumber;
    
    // keeps the time of creation of the topic (in seconds);
    @Size(max = 20)
    private LocalDateTime timeCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;
    
    public Topic() {
    }
    
    public Topic(String title, String description, User owner, Accesses access) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.access = access;
        this.entriesNumber = (long) 0;
        this.timeCreation = LocalDateTime.now();
    }
    
    public String getTimeCreationAgo() {
        return CommonOperationsForControllers.getTimeAgo(this.timeCreation);
    }
    
    public Long getId() {
        return this.id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Accesses getAccess() {
        return this.access;
    }
    
    public Long getEntriesNumber() {
        return this.entriesNumber;
    }
    
    public LocalDateTime getTimeCreation() {
        return this.timeCreation;
    }
    
    public User getOwner() {
        return this.owner;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setAccess(Accesses access) {
        this.access = access;
    }
    
    public void setEntriesNumber(Long entriesNumber) {
        this.entriesNumber = entriesNumber;
    }
    
    public void setTimeCreation(LocalDateTime timeCreation) {
        this.timeCreation = timeCreation;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
