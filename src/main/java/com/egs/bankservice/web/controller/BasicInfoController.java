package com.egs.bankservice.web.controller;

import com.egs.bankservice.service.CardService;
import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/basic-info")
public class BasicInfoController {
    private CardService cardService;

    public BasicInfoController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(value = "/addAccount", method = RequestMethod.POST)
    public BankRestResponse addAccount(@RequestBody AccountDto accountDto) {
        return cardService.addAccount(accountDto);

    }

    @RequestMapping(value = "/addCard", method = RequestMethod.POST)
    public BankRestResponse addCard(@RequestBody CardDto cardDto) {
        return cardService.addCard(cardDto);

    }

}
