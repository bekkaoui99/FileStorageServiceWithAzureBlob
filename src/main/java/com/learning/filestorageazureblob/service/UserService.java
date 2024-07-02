package com.learning.filestorageazureblob.service;

import com.learning.filestorageazureblob.UserRepository;
import com.learning.filestorageazureblob.dto.request.UserRequest;
import com.learning.filestorageazureblob.entity.User;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FileStorageServiceImpl fileStorageService;


    public UserService(UserRepository userRepository, FileStorageServiceImpl fileStorageService) {
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    public User create(UserRequest userRequest){
        String imageUrl;
        imageUrl = this.fileStorageService.uploadFile(userRequest.file());
        User user = User.builder()
                .userName(userRequest.userName())
                .imageUrl(imageUrl)
                .build();
        return this.userRepository.save(user);
    }
}
