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
import org.hibernate.annotations.Type;

@Entity
@Table(name = "entries")
public class Entry {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
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
    
    public Entry () {
    }
    
    public Entry (String text, Topic topic) {
        this.text = text;
        this.topic = topic;
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
