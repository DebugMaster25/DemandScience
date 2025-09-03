package com.example.fileapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final String storageLocation = "uploads/";

    @Autowired
    public FileService() {
        // Create the storage directory if it doesn't exist
        File directory = new File(storageLocation);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        Path path = Paths.get(storageLocation + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path);
        return path.toString();
    }

    public byte[] retrieveFile(String filename) throws IOException {
        Path path = Paths.get(storageLocation + filename);
        return Files.readAllBytes(path);
    }
}