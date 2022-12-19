package com.bankSimulation.controller;

import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.dto.TransactionDTO;
import com.bankSimulation.service.AccountService;
import com.bankSimulation.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/make-transfer")
    public String makeTransfer(Model model){

        //we need all accounts to provide them as sender, receiver
        model.addAttribute("accounts",accountService.listAllAccount());
        //we need empty transaction object to get info from UI
        model.addAttribute("transaction", TransactionDTO.builder().build());
        //we need list of last 10 transactions
        model.addAttribute("lastTransactions",transactionService.lastTransactionsList());

        return "transaction/make-transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@Valid TransactionDTO transactionDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){

            model.addAttribute("accounts", accountService.listAllAccount());
            return "transaction/make-transfer";
        }

        AccountDTO sender = accountService.findByID(transactionDTO.getSender());
        AccountDTO receiver = accountService.findByID(transactionDTO.getReceiver());
        transactionService.makeTransfer(sender,receiver, transactionDTO.getAmount(),new Date(), transactionDTO.getMessage());
        return "redirect:/make-transfer";
    }

    @GetMapping("/transaction/{id}")
    public String userTransactions(@PathVariable("id") UUID id, Model model){
        List<TransactionDTO> transactionDTOS = transactionService.retrieveAllTransactionByID(id);
        model.addAttribute("transactions", transactionDTOS);


        return "transaction/transactions";

    }
}