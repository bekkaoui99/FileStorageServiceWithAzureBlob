package com.learning.filestorageazureblob.controller;


import com.learning.filestorageazureblob.dto.request.UserRequest;
import com.learning.filestorageazureblob.dto.response.UserResponse;
import com.learning.filestorageazureblob.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public UserResponse create(@ModelAttribute UserRequest userRequest){
        return this.userService.create(userRequest);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id ,@ModelAttribute UserRequest userRequest){
        return this.userService.update(userRequest , id);
    }

    @DeleteMapping("/{id}")
    public UserResponse delete(@PathVariable Long id ){
        return this.userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserResponse findUser(@PathVariable Long id ){
        return this.userService.getOne(id);
    }

    @GetMapping("/list")
    public List<UserResponse> findUsers(){
        return this.userService.getAll();
    }

    @GetMapping("/page")
    public Page<UserResponse> findUsers(
            @RequestParam(name = "pageNumber" ,defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize" ,defaultValue = "0") int pageSize
    ){
        return this.userService.getAll(pageNumber , pageSize);
    }

}
