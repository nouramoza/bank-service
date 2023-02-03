package com.egs.bankservice.web.api;

import com.egs.bankservice.service.BankService;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card-service")
public class CardResourceApi {

    Logger log = LoggerFactory.getLogger(CardResourceApi.class);
    private final BankService bankService;

    public CardResourceApi(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping(value = "/validateCardNumber")
    public BankRestResponse<CardDto> validateCardNumber(@RequestBody CardDto cardDto) {
        return bankService.getCardVerification(cardDto);
    }

    @PostMapping(value = "/validateCardPinNumber")
    public BankRestResponse<CardDto> validateCardPinNumber(@RequestBody CardDto cardDto) {
        return bankService.getCardPinVerification(cardDto);
    }
}

