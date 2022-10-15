package com.bankSimulation.controller;

import com.bankSimulation.model.Account;
import com.bankSimulation.model.Transaction;
import com.bankSimulation.service.AccountService;
import com.bankSimulation.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
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
        model.addAttribute("transaction", Transaction.builder().build());
        //we need list of last 10 transactions
        model.addAttribute("lastTransactions",transactionService.lastTransactionsList());

        return "transaction/make-transfer";
    }

    @PostMapping("/transfer")
    public String transfer(Transaction transaction, Model model){
        Account sender = accountService.findByID(transaction.getSender());
        Account receiver = accountService.findByID(transaction.getReceiver());
        transactionService.makeTransfer(sender,receiver,transaction.getAmount(),new Date(),transaction.getMessage());
        return "redirect:/make-transfer";
    }

    @GetMapping("/transaction/{id}")
    public String userTransactions(@PathVariable("id") UUID id, Model model){
        List<Transaction> transactions = transactionService.retrieveAllTransactionByID(id);
        model.addAttribute("transactions",transactions);


        return "transaction/transactions";

    }
}