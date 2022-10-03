package com.bankSimulation.controller;

import com.bankSimulation.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
