package com.example.dto;


import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TransactionCreateRequest {

    @NotBlank
    private String receiver;
    @NotBlank
    private String sender;
    @Min(1)
    private Long amount;
    private String reason;


}
