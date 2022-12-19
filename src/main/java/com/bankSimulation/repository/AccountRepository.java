package com.bankSimulation.repository;

import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.entity.Account;
import com.bankSimulation.enums.AccountStatus;
import com.bankSimulation.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository  extends JpaRepository<Account, Long> {


    List<Account> findAllByAccountStatus(AccountStatus accountStatus);
}
