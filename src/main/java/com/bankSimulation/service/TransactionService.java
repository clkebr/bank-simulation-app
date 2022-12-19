package com.bankSimulation.service;

import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransactionService {

    TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message);
    
    List<TransactionDTO> findAllTransactions();


    List<TransactionDTO> lastTransactionsList();

    List<TransactionDTO> retrieveAllTransactionByID(Long id);

}
