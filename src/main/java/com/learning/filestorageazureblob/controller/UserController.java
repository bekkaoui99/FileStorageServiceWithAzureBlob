package com.learning.filestorageazureblob.controller;


import com.learning.filestorageazureblob.dto.request.UserRequest;
import com.learning.filestorageazureblob.entity.User;
import com.learning.filestorageazureblob.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@ModelAttribute UserRequest user){
        User createdUser = this.userService.create(user);
        return User.builder()
                .userName(createdUser.getUserName())
                .imageUrl(createdUser.getImageUrl())
                .build();

    }

}
