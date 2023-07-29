package com.example.service;

import com.example.dto.TransactionCreateRequest;
import com.example.models.Transaction;
import com.example.models.TransactionStatus;
import com.example.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service

public class TransactionService {
    @Autowired

    TransactionRepository transactionRepository;

    @Autowired
    KafkaTemplate<String, String > kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String TRANSACTION_CREATED_TOPIC = "transaction_created";
    private static final String TRANSACTION_COMPLETED_TOPIC = "transaction_completed";
    private static final String WALLET_UPDATED_TOPIC ="wallet_updated";

    private static final String WALLET_UPDATE_SUCCESS_STATUS = "SUCCESS";
    private static final String WALLET_UPDATE_FAILED_STATUS = "FAILED";

    public String transact(TransactionCreateRequest request) throws JsonProcessingException {

        Transaction transaction  = Transaction.builder()
                .senderId(request.getSender())//taking from FE
                .receiverId(request.getReceiver())//FE
                .externalId(UUID.randomUUID().toString()) //BE
                .transactionStatus(TransactionStatus.PENDING) //BE
                .reason(request.getReason()) //FE
                .amount(request.getAmount()) //FE

                .build();

        transactionRepository.save(transaction);

        JSONObject obj = new JSONObject();
        obj.put("senderId",transaction.getSenderId());
        obj.put("receiverId",transaction.getReceiverId());
        obj.put("amount" , transaction.getAmount());
        obj.put("transactionID", transaction.getExternalId());

        kafkaTemplate.send(TRANSACTION_CREATED_TOPIC, objectMapper.writeValueAsString(obj));


        return transaction.getExternalId();


    }
    @KafkaListener(topics = {WALLET_UPDATED_TOPIC}, groupId = "jdbl123")
    public  void updateTransaction(String msg) throws ParseException , JsonProcessingException{

        JSONObject obj = (JSONObject) new JSONParser()
                .parse(msg) ;

        String externalTransactionId = (String) obj.get("transactionId");
        String receiverPhoneNumber = (String) obj.get("receiverWalletId");
        String senderPhoneNumber = (String) obj.get("senderWalletId");
        String walletUpdateStatus = (String) obj.get("status");
        Long amount = (Long) obj.get("amount");

        TransactionStatus transactionStatus;

        if(walletUpdateStatus.equals(WALLET_UPDATE_FAILED_STATUS)){
            transactionStatus = TransactionStatus.FAILED;
            transactionRepository.updateTransaction(externalTransactionId, transactionStatus);
        }else{
            transactionStatus = TransactionStatus.SUCCESSFUL;
            transactionRepository.updateTransaction(externalTransactionId, transactionStatus);
        }

    }
}
