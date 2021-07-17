package com.example.LearningLog.models;

import com.example.LearningLog.models.util.EntryHelper;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(
            targetEntity = Upload.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )

    private List<Upload> uploads;

    @ManyToMany
    @JoinTable(
            name = "entry_likes",
            joinColumns = { @JoinColumn(name = "entry_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> likes = new HashSet<>();

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

    public String getDateTimeFormatted() {
        return EntryHelper.formatDateTime(this.dateTime);
    }

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
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

    public Set<User> getLikes() {
        return likes;
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

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }
}
