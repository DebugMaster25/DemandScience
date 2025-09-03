package com.example.fileapp.service;

import com.example.fileapp.util.EmailValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FileService {

    public enum FileStatus { IN_PROGRESS, COMPLETED, NOT_FOUND }

    private final Map<String, FileStatus> fileStatusMap = new ConcurrentHashMap<>();
    private final Map<String, String> filePathMap = new ConcurrentHashMap<>();
    private final Path storageDir = Paths.get("uploaded_files");

    public FileService() throws IOException {
        Files.createDirectories(storageDir);
    }

    public String processAndStoreFile(MultipartFile file) throws IOException {
        String id = UUID.randomUUID().toString();
        fileStatusMap.put(id, FileStatus.IN_PROGRESS);

        // Process file
        List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream()))
                .lines().collect(Collectors.toList());
        if (lines.isEmpty()) throw new IllegalArgumentException("Empty file");

        String header = lines.get(0) + ",flag";
        List<String> processed = new ArrayList<>();
        processed.add(header);

        for (int i = 1; i < lines.size(); i++) {
            String row = lines.get(i);
            if (row.trim().isEmpty()) continue;
            boolean hasEmail = Arrays.stream(row.split(","))
                    .anyMatch(EmailValidator::isValidEmail);
            processed.add(row + "," + hasEmail);
        }

        Path filePath = storageDir.resolve(id + ".csv");
        Files.write(filePath, processed);
        filePathMap.put(id, filePath.toString());
        fileStatusMap.put(id, FileStatus.COMPLETED);
        return id;
    }

    public FileStatus getFileStatus(String id) {
        return fileStatusMap.getOrDefault(id, FileStatus.NOT_FOUND);
    }

    public byte[] getFileData(String id) {
        String path = filePathMap.get(id);
        if (path == null) return null;
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return null;
        }
    }
}