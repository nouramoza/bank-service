package com.egs.bankservice.web.api;

import com.egs.bankservice.service.CardService;
import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basic-info")
public class BasicInfoResourceApi {
    private CardService cardService;

    public BasicInfoResourceApi(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping(value = "/addAccount")
    public BankRestResponse addAccount(@RequestBody AccountDto accountDto) {
        return cardService.addAccount(accountDto);
    }

    @PostMapping(value = "/addCard")
    public BankRestResponse addCard(@RequestBody CardDto cardDto) {
        return cardService.addCard(cardDto);
    }

}
