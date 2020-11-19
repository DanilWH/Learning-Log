package com.example.LearningLog.service;

import com.example.LearningLog.models.Entry;
import com.example.LearningLog.models.Upload;
import com.example.LearningLog.repos.UploadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UploadService {

    @Autowired
    private UploadRepo uploadRepo;

    public void uploadFiles(Entry entry, List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            // make the file as a JPA object.
            Upload upload = new Upload();

            upload.setFilename(file.getOriginalFilename());
            upload.setBytes(file.getBytes());
            upload.setEntry(entry);

            this.uploadRepo.save(upload);
        }
    }
}
