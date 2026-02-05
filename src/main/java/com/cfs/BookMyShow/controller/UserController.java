package com.cfs.BookMyShow.controller;

import com.cfs.BookMyShow.dto.UserDto;
import com.cfs.BookMyShow.repository.UserRepository;
import com.cfs.BookMyShow.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework. web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addNew")
    public ResponseEntity<UserDto> addNewUser(@RequestBody UserDto userDto){
        UserDto result = userService.addUser(userDto);
        return ResponseEntity.ok(result);

    }


}
