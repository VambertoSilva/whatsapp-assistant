package com.vamberto.whatsapp.assistant;

import com.vamberto.whatsapp.assistant.Models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {
}
