package com.learning.filestorageazureblob.controller;


import com.learning.filestorageazureblob.service.FileStorageServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/images")
public class FileStorageControllerTest {

    private final FileStorageServiceImpl fileStorageService;

    public FileStorageControllerTest(FileStorageServiceImpl fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = fileStorageService.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully. URL: " + fileUrl);
    }

    @PutMapping("/update/{blobName}")
    public ResponseEntity<String> updateFile(@PathVariable String blobName, @RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = fileStorageService.updateFile(blobName, file);
        return ResponseEntity.ok("File updated successfully. URL: " + fileUrl);
    }

    @GetMapping("/download/{blobName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String blobName) throws IOException {
        InputStream inputStream = fileStorageService.downloadFile(blobName);
        byte[] content = inputStream.readAllBytes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + blobName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(content);
    }

    @DeleteMapping("/delete/{blobName}")
    public ResponseEntity<String> deleteFile(@PathVariable String blobName) {
        fileStorageService.deleteFile(blobName);
        return ResponseEntity.ok("File deleted successfully");
    }

}
