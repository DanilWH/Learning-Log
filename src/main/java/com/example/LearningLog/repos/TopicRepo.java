package com.example.LearningLog.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LearningLog.models.Accesses;
import com.example.LearningLog.models.Topic;

public interface TopicRepo extends JpaRepository<Topic, Integer> {

    Iterable<Topic> findByOwnerId(Long owner_id);
    Iterable<Topic> findByAccess(Accesses access);
    Optional<Topic> findById(Long id);
    
}
