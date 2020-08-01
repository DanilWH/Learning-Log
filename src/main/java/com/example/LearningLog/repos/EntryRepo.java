package com.example.LearningLog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LearningLog.models.Entry;

public interface EntryRepo extends JpaRepository<Entry, Long> {

    Iterable<Entry> findByTopicId(Long topic_id);
    Iterable<Entry> findByTopicIdOrderByDateTime(Long topic_id);
    
}
