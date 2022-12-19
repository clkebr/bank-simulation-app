package com.bankSimulation.service;

import com.bankSimulation.enums.AccountType;
import com.bankSimulation.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {

    void createNewAccount(AccountDTO accountDTO);

    List<AccountDTO> listAllAccount();

    void deleteAccount(Long id);

    AccountDTO findByID(Long id);

    List<AccountDTO> findAllActive();

    void updateAccount(AccountDTO accountDTO);
}
