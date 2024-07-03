package com.learning.filestorageazureblob.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface IFileStorageService {



     String uploadFile(MultipartFile file);
     Set<String> uploadFile(Set<MultipartFile> files);

     String updateFile(String filename, MultipartFile file) throws IOException;
     Set<String> updateFile(Set<String> filename, Set<MultipartFile> files) throws IOException;

     InputStream downloadFile(String filename);

     void deleteFile(String filename);
     void deleteFile(Set<String> filename);
}
