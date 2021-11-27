package com.egs.bankservice.service.serviceImpl;

import com.egs.bankservice.entity.AccountEntity;
import com.egs.bankservice.entity.CardEntity;
import com.egs.bankservice.repository.AccountRepository;
import com.egs.bankservice.repository.CardRepository;
import com.egs.bankservice.service.CardService;
import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public BankRestResponse addCard(CardDto cardDto) {

        BankRestResponse restResponse;

        AccountEntity accountEntity = accountRepository.findByAccountNumber(cardDto.getAccountDto().getAccountNumber());

        CardEntity cardEntity = new CardEntity(null, accountEntity, cardDto.getCardNumber(),
                cardDto.getCvv2(), cardDto.getExpireDate(),
                cardDto.getPin(), 0, cardDto.getIsActive());

        cardRepository.saveAndFlush(cardEntity);
//        cardRepository.flush();
        return new BankRestResponse(BankRestResponse.STATUS.SUCCESS, cardEntity.getId().toString());
    }

    @Override
    public BankRestResponse addAccount(AccountDto accountDto) {
        BankRestResponse restResponse;

        AccountEntity accountEntity = new AccountEntity(null, accountDto.getAccountNumber(),
                accountDto.getPersonId(), accountDto.getBalance(), accountDto.getIsActive());

        accountRepository.save(accountEntity);
        accountRepository.flush();
        return new BankRestResponse(BankRestResponse.STATUS.SUCCESS, accountEntity.getId().toString());
    }
}
