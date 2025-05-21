package com.vamberto.whatsapp.assistant.Services;

import com.vamberto.whatsapp.assistant.Models.Transactions;
import com.vamberto.whatsapp.assistant.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;

    public void createTransactions(Transactions transaction){
        transactionsRepository.save(transaction);
    }
}
