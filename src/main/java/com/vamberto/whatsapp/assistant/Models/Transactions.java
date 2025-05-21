package com.vamberto.whatsapp.assistant.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

//    depois adicioar as collumns e regras com not null

    private BigDecimal amount;
    private Timestamp date;
    private String type;
    private String description;

}
