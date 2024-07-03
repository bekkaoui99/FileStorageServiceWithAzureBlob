package com.learning.filestorageazureblob.mapper;

import com.learning.filestorageazureblob.dto.request.UserRequest;
import com.learning.filestorageazureblob.dto.response.UserResponse;
import com.learning.filestorageazureblob.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {


    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponse userToUserResponse(User user){
        return UserResponse.builder()
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .build();
    }

    public User userToUserResponse(UserRequest userRequest){
        return User.builder()
                .userName(userRequest.userName())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();
    }

    // in this example , I used ModelMapper rather than create my own implementation to map a request to entity
    // it helps us , saving time and focusing on other important thing .
    public User updateUserFields(UserRequest userRequest , User user){
        this.modelMapper.map(userRequest , user);
        return user;
    }

// Here you can see the difference , I recreated the same methods ,but this time I used Model Mapper .

//    public UserResponse userToUserResponse(User user){
//        return this.modelMapper.map(user , UserResponse.class);
//    }

//    public User userToUserResponse(UserRequest userRequest){
//        return this.modelMapper.map(userRequest , User.class);
//    }

}
