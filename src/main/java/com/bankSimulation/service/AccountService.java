package com.bankSimulation.service;

import com.bankSimulation.enums.AccountType;
import com.bankSimulation.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {

    AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);

    List<AccountDTO> listAllAccount();

    void deleteAccount(Long id);

    AccountDTO findByID(Long id);
}
