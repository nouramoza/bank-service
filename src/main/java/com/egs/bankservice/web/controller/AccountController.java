package com.egs.bankservice.web.controller;

import com.egs.bankservice.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account-service")
public class AccountController {
    Logger log = LoggerFactory.getLogger(BankController.class);
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


}
