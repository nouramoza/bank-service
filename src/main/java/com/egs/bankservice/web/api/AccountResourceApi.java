package com.egs.bankservice.web.api;

import com.egs.bankservice.service.AccountService;
import com.egs.bankservice.web.dto.AccountRequestDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account-service")
public class AccountResourceApi {
    Logger log = LoggerFactory.getLogger(AccountResourceApi.class);
    private AccountService accountService;

    public AccountResourceApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/requestManagement")
    public BankRestResponse requestManagement(@RequestBody AccountRequestDto accountRequestDto) {
        return accountService.requestManagement(accountRequestDto);

    }
}
