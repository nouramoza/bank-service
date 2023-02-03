package com.egs.bankservice.service.impl;

import com.egs.bankservice.entity.AccountEntity;
import com.egs.bankservice.entity.CardEntity;
import com.egs.bankservice.repository.AccountRepository;
import com.egs.bankservice.repository.CardRepository;
import com.egs.bankservice.service.BasicInfoService;
import com.egs.bankservice.util.ObjectMapperUtils;
import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BasicInfoServiceImpl implements BasicInfoService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public BankRestResponse addAccount(AccountDto accountDto) {
        AccountEntity accountEntity = new AccountEntity(null, accountDto.getAccountNumber(),
                accountDto.getPersonId(), accountDto.getBalance(), accountDto.getIsActive(),
                LocalDateTime.now(), null);

        accountRepository.saveAndFlush(accountEntity);

        AccountDto resultAccount = ObjectMapperUtils.map(accountEntity, AccountDto.class);
        return new BankRestResponse(BankRestResponse.STATUS.SUCCESS, accountEntity.getId().toString(), resultAccount);
    }

    @Override
    public BankRestResponse addCard(CardDto cardDto) {

        AccountEntity accountEntity = accountRepository.findByAccountNumber(cardDto.getAccountDto().getAccountNumber());

        CardEntity cardEntity = new CardEntity(null, accountEntity, cardDto.getCardNumber(),
                cardDto.getCvv2(), LocalDateTime.now(), cardDto.getExpireDate(),
                cardDto.getPin(), cardDto.getIncorrectPinCount(), cardDto.getIsActive());

        cardRepository.saveAndFlush(cardEntity);
        CardDto resultCardDto = ObjectMapperUtils.map(cardEntity, CardDto.class);
        return new BankRestResponse(BankRestResponse.STATUS.SUCCESS, cardEntity.getId().toString(), resultCardDto);
    }

    @Override
    public BankRestResponse activeCard(String cardNumber) {
        CardEntity cardEntity = cardRepository.findByCardNumber(cardNumber);
        cardEntity.setIsActive(true);
        cardEntity.setIncorrectPinCount(0);
        cardRepository.saveAndFlush(cardEntity);
        CardDto resultCardDto = ObjectMapperUtils.map(cardEntity, CardDto.class);

        return new BankRestResponse(BankRestResponse.STATUS.SUCCESS, cardEntity.getId().toString(), resultCardDto);
    }
}
