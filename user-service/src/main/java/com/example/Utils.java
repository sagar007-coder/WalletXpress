package com.example;

import com.example.dto.CreateUserRequest;
import com.example.dto.GetUserResponse;
import com.example.model.User;

public class Utils {
    public static User convertUserCreateRequest(CreateUserRequest request){
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .age(request.getAge())
                .build();
    }
    public static GetUserResponse convertToUserResponse(User user){
        return GetUserResponse.builder()
                .age(user.getAge())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .updatedONn(user.getUpdatedOn())
                .createdOn(user.getCreatedOn())
                .build();
    }

}
