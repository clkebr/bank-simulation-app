package com.bankSimulation.service.impl;

import com.bankSimulation.enums.AccountType;
import com.bankSimulation.exception.AccountOwnershipException;
import com.bankSimulation.exception.BadRequestException;
import com.bankSimulation.exception.BalanceNotSufficientException;
import com.bankSimulation.exception.UnderConstructionException;
import com.bankSimulation.model.Account;
import com.bankSimulation.model.Transaction;
import com.bankSimulation.repository.AccountRepository;
import com.bankSimulation.repository.TransactionRepository;
import com.bankSimulation.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        if(!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

            Transaction transaction = Transaction.builder()
                    .amount(amount).sender(sender.getId())
                    .receiver(receiver.getId()).creationDate(creationDate).message(message).build();

            return transactionRepository.save(transaction);
        }else {
            throw new UnderConstructionException("App is under construction, try again later");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if(checkSenderBalance(sender,amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
       return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(Account sender, Account receiver) {
        if((sender.getAccountType().equals(AccountType.SAVING) ||receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId()))
        {
            throw new AccountOwnershipException("If one of the account is saving, userId must be the same");
        }
    }


    private void validateAccount(Account sender, Account receiver) {
        /*
            -if any of the account is null
            -if account ids are the same(same account)
            -if the account exist in the database(repository)
         */
        if(sender == null || receiver == null){
            throw new BadRequestException("Sender or receiver can not be null");
        }
        if(sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than receiver");
        }

        findAccountById(sender.getId());
        findAccountById(receiver.getId());


    }

    private void findAccountById(UUID id) {
        accountRepository.findById(id);
    }


    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> lastTransactionsList() {
        return transactionRepository.lastTransactions();

    }

    @Override
    public List<Transaction> retrieveAllTransactionByID(UUID id) {
        return transactionRepository.findTransactionsByID(id);
    }
}
