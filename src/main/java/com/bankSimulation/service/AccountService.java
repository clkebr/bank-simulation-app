package com.bankSimulation.service;

import com.bankSimulation.enums.AccountType;
import com.bankSimulation.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {

    Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType,Long userId);

    List<Account> listAllAccount();
}
