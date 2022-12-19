package com.bankSimulation.mapper;

import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountDTO convertDTO (Account account){
        return modelMapper.map(account, AccountDTO.class);
    }

    public Account convertEntity (AccountDTO dto){
        return modelMapper.map(dto, Account.class);
    }

}
