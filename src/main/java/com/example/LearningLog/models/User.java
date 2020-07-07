package com.example.LearningLog.models;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "usr")
public class User {
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean active;

    // allows to shape an additional table for storing an enum.
    @ElementCollection(targetClass=Role.class, fetch=FetchType.EAGER)
    // this allows us to create the table "user_role" for roles set that is joined with
    // the current table ("usr" table) using user_id.
    @CollectionTable(name="user_role", joinColumns=@JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING) // we want the enum is stored as a string.
    private Set<Role> roles;

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }
   
    public String getPassword() {
        return this.password;
    }

    public boolean getActive() {
        return this.active;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
