package com.example.LearningLog.repos;

import org.springframework.data.repository.CrudRepository;

import com.example.LearningLog.domains.Topic;

public interface TopicRepo extends CrudRepository<Topic, Integer>{

}
