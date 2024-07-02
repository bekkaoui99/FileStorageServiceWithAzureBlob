package com.learning.filestorageazureblob.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/test/images")
public class FileStorageController {


    private final BlobClientBuilder blobClientBuilder;

    public FileStorageController(BlobClientBuilder blobClientBuilder) {
        this.blobClientBuilder = blobClientBuilder;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        BlobClient blobClient = blobClientBuilder.blobName(fileName).buildClient();
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        BlobHttpHeaders headers = new BlobHttpHeaders()
                .setContentType(file.getContentType());
        blobClient.setHttpHeaders(headers);

        return ResponseEntity.ok("File uploaded successfully: " + fileName);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws IOException {
        BlobClient blobClient = blobClientBuilder.blobName(fileName).buildClient();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClient.download(outputStream);

        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(blobClient.getProperties().getContentType()));
        headers.setContentDispositionFormData("attachment", encodedFileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(outputStream.toByteArray());
    }

}
