package com.example.controller;

import com.example.dto.TransactionCreateRequest;
import com.example.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public String transact(@RequestBody @Valid TransactionCreateRequest request) throws JsonProcessingException {
        return transactionService.transact(request);
    }
}