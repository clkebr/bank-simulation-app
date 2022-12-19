package com.bankSimulation.repository;

import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.entity.Account;
import com.bankSimulation.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository  extends JpaRepository<Account, Long> {



}
