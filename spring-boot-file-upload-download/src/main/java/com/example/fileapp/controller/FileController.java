package com.example.fileapp.controller;

import com.example.fileapp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/API")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String id = fileService.processAndStoreFile(file);
            return ResponseEntity.ok(Map.of("id", id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Upload failed"));
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable String id) {
        FileService.FileStatus status = fileService.getFileStatus(id);
        if (status == FileService.FileStatus.NOT_FOUND) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid ID"));
        }
        if (status == FileService.FileStatus.IN_PROGRESS) {
            return ResponseEntity.status(423).body(Map.of("error", "Job in progress"));
        }
        byte[] fileData = fileService.getFileData(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".csv\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }
}