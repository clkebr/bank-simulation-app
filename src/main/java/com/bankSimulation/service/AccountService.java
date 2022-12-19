package com.bankSimulation.service;

import com.bankSimulation.enums.AccountType;
import com.bankSimulation.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);

    List<AccountDTO> listAllAccount();

    void deleteAccount(UUID id);

    AccountDTO findByID(UUID id);
}
