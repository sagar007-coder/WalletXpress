package com.example.models;

import lombok.*;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Transaction {


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String externalId;

   private String senderId;

   private String receiverId;

   private Long amount;

   private String reason;


   @Enumerated(value = EnumType.STRING)
   private TransactionStatus transactionStatus;

    @CreationTimestamp
   private  Date createdOn;

    @UpdateTimestamp
   private Date updatedOn;


}
