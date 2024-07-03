package com.learning.filestorageazureblob.service.impl;

import com.learning.filestorageazureblob.dto.response.UserResponse;
import com.learning.filestorageazureblob.exception.ResourceNotFoundException;
import com.learning.filestorageazureblob.mapper.UserMapper;
import com.learning.filestorageazureblob.repository.UserRepository;
import com.learning.filestorageazureblob.dto.request.UserRequest;
import com.learning.filestorageazureblob.entity.User;
import com.learning.filestorageazureblob.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileStorageServiceImpl fileStorageService;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, FileStorageServiceImpl fileStorageService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.fileStorageService = fileStorageService;
    }

    public UserResponse create(UserRequest userRequest){
        String imageUrl = "";
        if(userRequest.file() != null)
            imageUrl = this.fileStorageService.uploadFile(userRequest.file());

        User user = this.userMapper.userToUserResponse(userRequest);
        if(imageUrl != null)
            user.setImageUrl(imageUrl);

        User createdUser = this.userRepository.save(user);
        return this.userMapper.userToUserResponse(createdUser);
    }

    @Override
    public UserResponse update(UserRequest userRequest, Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with this ID : " + id));
        User updatedUserFields = this.userMapper.updateUserFields(userRequest, user);
        String updatedFile = fileStorageService.updateFile(user.getImageUrl(), userRequest.file());
        updatedUserFields.setImageUrl(updatedFile);
        User savedUser = this.userRepository.save(updatedUserFields);

        return this.userMapper.userToUserResponse(savedUser);
    }

    @Override
    public UserResponse delete(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with this ID : " + id));
        this.fileStorageService.deleteFile(user.getImageUrl());

        this.userRepository.delete(user);
        return this.userMapper.userToUserResponse(user);

    }

    @Override
    public UserResponse getOne(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with this ID : " + id));
        return this.userMapper.userToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        return this.userRepository.findAll()
                .stream()
                .map(userMapper::userToUserResponse)
                .toList();
    }

    @Override
    public Page<UserResponse> getAll(int pageNumber, int pageSize) {
        Page<User> userPage = this.userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<UserResponse> userResponseList = userPage.getContent()
                .stream()
                .map(userMapper::userToUserResponse)
                .toList();
        return new PageImpl<>(userResponseList , userPage.getPageable() , userPage.getTotalElements());
    }
}
