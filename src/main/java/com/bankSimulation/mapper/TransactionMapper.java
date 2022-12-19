package com.bankSimulation.mapper;


import com.bankSimulation.dto.TransactionDTO;
import com.bankSimulation.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionDTO convertDTO (Transaction transaction){
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    public Transaction convertEntity (TransactionDTO dto){
        return modelMapper.map(dto, Transaction.class);
    }
}
