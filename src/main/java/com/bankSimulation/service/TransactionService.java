package com.bankSimulation.service;

import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message);
    
    List<TransactionDTO> findAllTransactions();


    List<TransactionDTO> lastTransactionsList();

    List<TransactionDTO> retrieveAllTransactionByID(UUID id);

}
