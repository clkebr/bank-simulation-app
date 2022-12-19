package com.bankSimulation.service.impl;

import com.bankSimulation.entity.Account;
import com.bankSimulation.enums.AccountStatus;
import com.bankSimulation.enums.AccountType;
import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.mapper.AccountMapper;
import com.bankSimulation.repository.AccountRepository;
import com.bankSimulation.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {

        Account account = accountMapper.convertEntity(accountDTO);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setCreationDate(new Date());
        accountRepository.save(account);
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        return accountRepository.findAll().stream().map(accountMapper::convertDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id).get();
        account.setAccountStatus(AccountStatus.DELETED);
        accountRepository.save(account);
    }

    @Override
    public AccountDTO findByID(Long id) {
        return accountMapper.convertDTO(accountRepository.findById(id).get());
    }

    @Override
    public List<AccountDTO> findAllActive() {
        return accountRepository.findAllByAccountStatus(AccountStatus.ACTIVE).stream()
                .map(accountMapper::convertDTO).collect(Collectors.toList());
    }
}
