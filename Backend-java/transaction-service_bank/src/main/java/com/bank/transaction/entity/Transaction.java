package com.bank.transaction.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(unique = true, nullable = false)
private String txId; // UUID

private Long fromAccountId;

private Long toAccountId;

private double amount;

private String type; 

@Temporal(TemporalType.TIMESTAMP)
private Date timestamp;

private String status; 
}