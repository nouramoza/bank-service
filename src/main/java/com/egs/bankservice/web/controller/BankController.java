package com.egs.bankservice.web.controller;

import com.egs.bankservice.service.AccountService;
import com.egs.bankservice.service.BankService;
import com.egs.bankservice.web.dto.AccountRequestDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank-service")
public class BankController {

    Logger log = LoggerFactory.getLogger(BankController.class);
    private BankService bankService;
    private AccountService accountService;

    public BankController(BankService bankService, AccountService accountService) {
        this.bankService = bankService;
        this.accountService = accountService;
    }

    @RequestMapping(value = "/validateCardNumber", method = RequestMethod.POST)
    public BankRestResponse validateCardNumber(@RequestBody CardDto cardDto) {
        return bankService.getCardVerification(cardDto);

    }

    @RequestMapping(value = "/validateCardPinNumber", method = RequestMethod.POST)
    public BankRestResponse validateCardPinNumber(@RequestBody CardDto cardDto) {
        return bankService.getCardPinVerification(cardDto);

    }

    @RequestMapping(value = "/balanceManagement", method = RequestMethod.POST)
    public BankRestResponse balanceManagement(@RequestBody AccountRequestDto accountRequestDto) {
        return accountService.balanceManagement(accountRequestDto);

    }

}
