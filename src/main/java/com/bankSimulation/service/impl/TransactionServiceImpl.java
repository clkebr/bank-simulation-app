package com.bankSimulation.service.impl;

import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.dto.TransactionDTO;
import com.bankSimulation.entity.Account;
import com.bankSimulation.enums.AccountType;
import com.bankSimulation.exception.AccountOwnershipException;
import com.bankSimulation.exception.BadRequestException;
import com.bankSimulation.exception.BalanceNotSufficientException;
import com.bankSimulation.exception.UnderConstructionException;
import com.bankSimulation.mapper.TransactionMapper;
import com.bankSimulation.repository.AccountRepository;
import com.bankSimulation.repository.TransactionRepository;
import com.bankSimulation.service.AccountService;
import com.bankSimulation.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;
    AccountService accountService;
    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;

    public TransactionServiceImpl(AccountService accountService, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public void makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {
        if(!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

            TransactionDTO transactionDTO = new TransactionDTO(sender,receiver,amount,message,creationDate);

            transactionRepository.save(transactionMapper.convertEntity(transactionDTO));
        }else {
            throw new UnderConstructionException("App is under construction, try again later");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if(checkSenderBalance(sender,amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

            AccountDTO senderAcc = accountService.findByID(sender.getId());
            senderAcc.setBalance(sender.getBalance());
            //save again to database
            accountService.updateAccount(senderAcc);

            AccountDTO receiverAcc = accountService.findByID(receiver.getId());
            receiverAcc.setBalance(receiver.getBalance());

            accountService.updateAccount(receiverAcc);



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

    private AccountDTO findAccountById(Long id) {
       return accountService.findByID(id);
    }


    @Override
    public List<TransactionDTO> findAllTransactions() {
        return transactionRepository.findAll().stream().map(transactionMapper::convertDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> lastTransactionsList() {
        return transactionRepository.findLastTenTransaction().stream().map(transactionMapper::convertDTO).collect(Collectors.toList());

    }

    @Override
    public List<TransactionDTO> retrieveAllTransactionByID(Long id) {
        return transactionRepository.findTransactionById(id).stream().map(transactionMapper::convertDTO).collect(Collectors.toList());
    }
}
