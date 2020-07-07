package com.example.LearningLog.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "entries")
public class Entry {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Lob
    private String text;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Topic topic;
    
    public String getTopicTitle() {
        return this.topic.getTitle();
    }
    
    public Long getId() {
        return this.id;
    }
    
    public String getText() {
        return this.text;
    }
    
    public Topic getTopic() {
        return this.topic;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
