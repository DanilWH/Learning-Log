package com.example.LearningLog.repos;

import com.example.LearningLog.models.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepo extends JpaRepository<Upload, Long> {

    Upload findByFilename(String filename);

}
