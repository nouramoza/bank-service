package com.egs.bankservice.service;

import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import org.springframework.stereotype.Service;

@Service
public interface CardService {
    public BankRestResponse addCard(CardDto cardDto);

    public BankRestResponse addAccount(AccountDto accountDto);

}
