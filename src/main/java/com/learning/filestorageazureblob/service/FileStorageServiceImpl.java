package com.learning.filestorageazureblob.service;


import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileStorageServiceImpl {

    private final BlobClientBuilder blobClientBuilder;


    public FileStorageServiceImpl(BlobClientBuilder blobClientBuilder) {
        this.blobClientBuilder = blobClientBuilder;
    }

    public String uploadFile(MultipartFile file)  {
        String filename = file.getOriginalFilename();
        BlobClient blobClient = blobClientBuilder
                .blobName(filename)
                .buildClient();
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
        try {
            blobClient.upload(file.getInputStream(), file.getSize(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        blobClient.setHttpHeaders(headers);
        return blobClient.getBlobUrl(); // Return the URL of the uploaded blob
    }

    public String updateFile(String filename, MultipartFile file) throws IOException {
        BlobClient blobClient = blobClientBuilder
                .blobName(filename)
                .buildClient();
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
        blobClient.upload(file.getInputStream(), file.getSize(), true); // Upload will overwrite existing blob if true
        blobClient.setHttpHeaders(headers);
        return blobClient.getBlobUrl(); // Return the URL of the updated blob
    }

    public InputStream downloadFile(String filename) {
        BlobClient blobClient = blobClientBuilder
                .blobName(filename)
                .buildClient();
        return blobClient.openInputStream();
    }

    public void deleteFile(String filename) {
        BlobClient blobClient = blobClientBuilder
                .blobName(filename)
                .buildClient();
        blobClient.delete();
    }

}