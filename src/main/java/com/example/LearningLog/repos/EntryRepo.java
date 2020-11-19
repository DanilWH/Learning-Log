package com.example.LearningLog.repos;

import com.example.LearningLog.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepo extends JpaRepository<Entry, Long> {

    Iterable<Entry> findByTopicId(Long topic_id);
    List<Entry> findByTopicIdOrderByDateTimeDesc(Long topic_id);
    
}
