package com.egs.bankservice.service;

import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import org.springframework.stereotype.Service;

@Service
public interface BankService {

    public BankRestResponse getCardVerification(CardDto cardDto);

    public BankRestResponse getCardPinVerification(CardDto cardDto);

}
