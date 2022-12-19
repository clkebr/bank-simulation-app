package com.bankSimulation.service.impl;

import com.bankSimulation.enums.AccountStatus;
import com.bankSimulation.enums.AccountType;
import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.repository.AccountRepository;
import com.bankSimulation.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Component
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        AccountDTO accountDTO = new AccountDTO();


//                AccountDTO.builder().id(UUID.randomUUID())
//                .userId(userId).accountType(accountType).balance(balance)
//                .creationDate(creationDate).accountStatus(AccountStatus.ACTIVE).build();
       return accountRepository.save(accountDTO);
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.findById(id).setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public AccountDTO findByID(Long id) {
        return accountRepository.findById(id);
    }
}
