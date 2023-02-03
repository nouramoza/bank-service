package com.egs.bankservice.web.api;

import com.egs.bankservice.service.BasicInfoService;
import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basic-info")
public class BasicInfoResourceApi {
    private BasicInfoService basicInfoService;

    public BasicInfoResourceApi(BasicInfoService basicInfoService) {
        this.basicInfoService = basicInfoService;
    }

    @PostMapping(value = "/addAccount")
    public BankRestResponse addAccount(@RequestBody AccountDto accountDto) {
        return basicInfoService.addAccount(accountDto);
    }

    @PostMapping(value = "/addCard")
    public BankRestResponse addCard(@RequestBody CardDto cardDto) {
        return basicInfoService.addCard(cardDto);
    }

    @PutMapping(value = "/activeCard/{cardNumber}")
    public BankRestResponse activeCard(@PathVariable String cardNumber) {
        return basicInfoService.activeCard(cardNumber);
    }

}
