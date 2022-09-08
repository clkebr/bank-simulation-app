package com.bankSimulation.repository;

import com.bankSimulation.exception.RecordNotFoundException;
import com.bankSimulation.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {
    public static List<Account> accountList = new ArrayList<>();

    public Account save (Account account){
        accountList.add(account);
        return account;
    }

    public List<Account> findAll() {
        return accountList;
    }

    //find the account inside the list, if not throws RecordNotFoundException

    public Account findById(UUID id) {
       return accountList.stream().filter(account -> account.getId().equals(id))
               .findAny().orElseThrow(()-> new RecordNotFoundException( id + " this account does not exist in DB"));

    }
}
