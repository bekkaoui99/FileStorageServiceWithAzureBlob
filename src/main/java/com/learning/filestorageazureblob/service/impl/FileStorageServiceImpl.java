package com.learning.filestorageazureblob.service.impl;


import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.learning.filestorageazureblob.service.IFileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Service
public class FileStorageServiceImpl implements IFileStorageService {

    private final BlobClientBuilder blobClientBuilder;


    public FileStorageServiceImpl(BlobClientBuilder blobClientBuilder) {
        this.blobClientBuilder = blobClientBuilder;
    }

    public String uploadFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        BlobClient blobClient = blobClientBuilder
                .blobName(filename)
                .buildClient();
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());

        try {
            blobClient.upload(file.getInputStream(), file.getSize(), true);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        blobClient.setHttpHeaders(headers);

        return blobClient.getBlobUrl();
    }

    @Override
    public Set<String> uploadFile(Set<MultipartFile> files) {
        Set<String> filesUrl = new HashSet<>();
        for (MultipartFile file : files){
            String uploadedFile = uploadFile(file);
            filesUrl.add(uploadedFile);
        }
        return filesUrl;
    }

    public String updateFile(String blobFileUrl, MultipartFile file) {
        String blobName = extractBlobNameFromUrl(blobFileUrl);
        BlobClient blobClient = blobClientBuilder
                .blobName(blobName)
                .buildClient();
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());

        // Upload will overwrite existing blob if true
        try {
            blobClient.upload(file.getInputStream(), file.getSize(), true);
        } catch (Exception e) {
            throw new RuntimeException("file updated failed");
        }

        blobClient.setHttpHeaders(headers);

        return blobClient.getBlobUrl();
    }

    @Override
    public Set<String> updateFile(Set<String> blobFilesUrl, Set<MultipartFile> files) {
        Set<String> imagesUrl = new HashSet<>();
        for (String fileUrl : blobFilesUrl){
            deleteFile(fileUrl);
        }
        for (MultipartFile file : files){
            String uploadedFile = uploadFile(file);
            imagesUrl.add(uploadedFile);
        }
        return imagesUrl;
    }

    public InputStream downloadFile(String blobFileUrl) {
        String blobName = extractBlobNameFromUrl(blobFileUrl);
        BlobClient blobClient = blobClientBuilder
                .blobName(blobName)
                .buildClient();
        return blobClient.openInputStream();
    }


    public void deleteFile(String blobFileUrl) {
        String blobName = extractBlobNameFromUrl(blobFileUrl);
        BlobClient blobClient = blobClientBuilder
                .blobName(blobName)
                .buildClient();
        blobClient.delete();
    }

    @Override
    public void deleteFile(Set<String> blobFilesUrl) {
        for (String fileName : blobFilesUrl){
            deleteFile(fileName);
        }
    }

    private String extractBlobNameFromUrl(String blobFileUrl) {
        // Assuming the URL format is like: https://<account>.blob.core.windows.net/<container>/<blobName>
        return blobFileUrl.substring(blobFileUrl.lastIndexOf('/') + 1);
    }
}