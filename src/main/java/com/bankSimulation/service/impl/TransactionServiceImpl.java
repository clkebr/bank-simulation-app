package com.bankSimulation.service.impl;

import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.dto.TransactionDTO;
import com.bankSimulation.enums.AccountType;
import com.bankSimulation.exception.AccountOwnershipException;
import com.bankSimulation.exception.BadRequestException;
import com.bankSimulation.exception.BalanceNotSufficientException;
import com.bankSimulation.exception.UnderConstructionException;
import com.bankSimulation.repository.AccountRepository;
import com.bankSimulation.repository.TransactionRepository;
import com.bankSimulation.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {
        if(!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .amount(amount).sender(sender.getId())
                    .receiver(receiver.getId()).creationDate(creationDate).message(message).build();

            return transactionRepository.save(transactionDTO);
        }else {
            throw new UnderConstructionException("App is under construction, try again later");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if(checkSenderBalance(sender,amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
       return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {
        if((sender.getAccountType().equals(AccountType.SAVING) ||receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId()))
        {
            throw new AccountOwnershipException("If one of the account is saving, userId must be the same");
        }
    }


    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
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
    public List<TransactionDTO> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionDTO> lastTransactionsList() {
        return transactionRepository.lastTransactions();

    }

    @Override
    public List<TransactionDTO> retrieveAllTransactionByID(UUID id) {
        return transactionRepository.findTransactionsByID(id);
    }
}
