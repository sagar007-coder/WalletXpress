package com.example.dto;


import com.example.model.User;
import lombok.*;
import org.apache.kafka.clients.consumer.StickyAssignor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest
{

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    @Min(18)
    private  int age;

    User to(){
        return User.builder()
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .age(this.age)
                .build();

}
}
