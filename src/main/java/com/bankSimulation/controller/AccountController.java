package com.bankSimulation.controller;

import com.bankSimulation.dto.AccountDTO;
import com.bankSimulation.enums.AccountType;
import com.bankSimulation.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    public String account(Model model){
        model.addAttribute("accountList", accountService.listAllAccount());
        return "/account/index";
    }

    @GetMapping("/create-form")
    public String getForm(Model model){
        model.addAttribute("account", AccountDTO.builder().build());
        model.addAttribute("accountTypes", AccountType.values());
        return "/account/create-account";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("account") AccountDTO accountDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("accountTypes", AccountType.values());
            return "account/create-account";
        }
        accountService.createNewAccount(accountDTO.getBalance(),new Date(), accountDTO.getAccountType(), accountDTO.getUserId());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteAccount(@PathVariable("id") UUID id){

        //find the account and change status to DELETED
        accountService.deleteAccount(id);

        System.out.println(id);

        return "redirect:/index";
    }



}
