package com.bankSimulation.repository;

import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.exception.RecordNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountRepository {
    public static List<AccountDTO> accountDTOList = new ArrayList<>();

    public AccountDTO save (AccountDTO accountDTO){
        accountDTOList.add(accountDTO);
        return accountDTO;
    }

    public List<AccountDTO> findAll() {
        return accountDTOList;
    }

    //find the account inside the list, if not throws RecordNotFoundException

    public AccountDTO findById(Long id) {
       return accountDTOList.stream().filter(account -> account.getId().equals(id))
               .findAny().orElseThrow(()-> new RecordNotFoundException( id + " this account does not exist in DB"));

    }
}
