package com.example.LearningLog.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Size(max = 200)
    private String title;
    
    private Accesses access;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;
    
    public Topic() {
        
    }
    
    public Topic(String title, User owner, Accesses access) {
        this.title = title;
        this.owner = owner;
        this.access = access;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public Accesses getAccess() {
        return this.access;
    }
    
    public User getOwner() {
        return this.owner;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAccess(Accesses access) {
        this.access = access;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
