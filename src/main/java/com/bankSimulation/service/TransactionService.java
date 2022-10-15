package com.bankSimulation.service;

import com.bankSimulation.model.Account;
import com.bankSimulation.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message);
    
    List<Transaction> findAllTransactions();


    List<Transaction> lastTransactionsList();

    List<Transaction> retrieveAllTransactionByID(UUID id);

}
