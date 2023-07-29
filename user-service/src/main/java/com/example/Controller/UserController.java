package com.example.Controller;

import com.example.Utils;
import com.example.dto.CreateUserRequest;
import com.example.dto.GetUserResponse;
import com.example.model.User;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/user")
    public  void createUser(@RequestBody @Valid CreateUserRequest userRequest) throws JsonProcessingException {
            userService.create(Utils.convertUserCreateRequest(userRequest));

    }

    @GetMapping("/user/{userID}")
        public GetUserResponse getUser(@PathVariable("userID")int userID) throws Exception {
            User user =  userService.get(userID);
          return   Utils.convertToUserResponse(user);
    }
}
