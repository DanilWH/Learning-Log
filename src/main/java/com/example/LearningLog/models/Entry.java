package com.example.LearningLog.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entries")
public class Entry {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Size(max = 20)
    private LocalDateTime dateTime;
    
    /**
     * On Hibernate JPA MYSQL, using @lob plus @column annotation on a String field
     * gives "wrong column type, expected longtext but column type is text",
     * the problem can be solved with below example:
     * 
     * Solution: @Column(columnDefinition="TEXT")
     * 
     */
    // fine with PostgreSQL
    @Lob
    @Type(type = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Topic topic;

    @Transient
    private List<Upload> uploads;

    public Entry () {
    }

    public Entry (String text, Topic topic) {
        this.text = text;
        this.topic = topic;
        // add up the general number of entries in the topic that the new entry belongs to.
        this.topic.setEntriesNumber(this.topic.getEntriesNumber() + 1);
        
        this.dateTime = LocalDateTime.now();
        this.uploads = new ArrayList<Upload>();
    }

    @PreRemove
    public void substractEntriesNumber() {
        // subtract the general number of entries in the topic that the new entry belongs to.
        this.topic.setEntriesNumber(this.topic.getEntriesNumber() - 1);
    }

    public Long getId() {
        return this.id;
    }

    public String getDateTime() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMMM d, Y HH:mm");

        return dateFormat.format(this.dateTime);
    }

    public String getText() {
        return this.text;
    }
    
    public List<Upload> getUploads() {
        return this.uploads;
    }

    public Topic getTopic() {
        return this.topic;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setUploads(List<Upload> uploads) {
        this.uploads = uploads;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
