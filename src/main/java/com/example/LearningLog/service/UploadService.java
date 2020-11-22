package com.example.LearningLog.service;

import com.example.LearningLog.models.Upload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadService {

    public Upload createUploadObject(MultipartFile file) throws IOException {
        if (file.isEmpty()) return null;

        Upload upload = new Upload();

        upload.setFilename(file.getOriginalFilename());
        upload.setBytes(file.getBytes());

        return upload;
    }
}
