package com.example.LearningLog.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "entries")
public class Entry {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Size(max = 20)
    private String dateTime;
    
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
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, Y H:m");
        this.dateTime = dateFormat.format(new Date());
    }
    
    public Long getId() {
        return this.id;
    }
    
    public String getDateTime() {
        return this.dateTime;
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
    
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
