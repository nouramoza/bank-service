package com.egs.bankservice.service.serviceImpl;

import com.egs.bankservice.entity.AccountEntity;
import com.egs.bankservice.entity.CardEntity;
import com.egs.bankservice.enums.RequestTypeEnum;
import com.egs.bankservice.repository.AccountRepository;
import com.egs.bankservice.repository.CardRepository;
import com.egs.bankservice.service.AccountService;
import com.egs.bankservice.util.ConstantsUtil;
import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.AccountRequestDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.error.ErrorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AccountServiceImpl implements AccountService {

    public static final String VERIFICATION_KEY = "VerificationKey";

    @Autowired
    private HttpSession httpSession;

    private AccountRepository accountRepository;
    private CardRepository cardRepository;

    public AccountServiceImpl(AccountRepository accountRepository, CardRepository cardRepository) {
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public BankRestResponse balanceManagement(AccountRequestDto accountRequestDto) {
        BankRestResponse restResponse = null;
        try {
            AccountDto accountDto = null;
            if (accountRequestDto.getRequestType().equals(RequestTypeEnum.CHECK_BALANCE)) {
                accountDto = checkBalance(accountRequestDto);
                restResponse = new BankRestResponse(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.CommonMessage.YOUR_BALANCE + accountDto.getBalance());
            } else if (accountRequestDto.getRequestType().equals(RequestTypeEnum.DEPOSIT)) {
                accountDto = updateBalance(accountRequestDto);
                restResponse = new BankRestResponse(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.CommonMessage.SUCCESS_DEPOSIT + accountDto.getBalance());

            } else if (accountRequestDto.getRequestType().equals(RequestTypeEnum.WITHDRAW)) {
                accountRequestDto.setAmount(-accountRequestDto.getAmount());
                accountDto = updateBalance(accountRequestDto);
                restResponse = new BankRestResponse(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.CommonMessage.SUCCESS_WITHDRAW + accountDto.getBalance());
            }

        } catch (Exception e) {
            httpSession.setAttribute(VERIFICATION_KEY, ConstantsUtil.SessionKey.ERROR);
            restResponse = new BankRestResponse(BankRestResponse.STATUS.FAILURE, e.toString());
        }
        return restResponse;
    }

    private AccountDto checkBalance(AccountRequestDto accountRequestDto) {
        CardEntity cardEntity = cardRepository.findByCardNumber(accountRequestDto.getCardNumber());
        AccountEntity accountEntity = cardEntity.getAccountEntity();
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(accountEntity.getAccountNumber());
        accountDto.setBalance(accountEntity.getBalance());
        return accountDto;
    }

    private AccountDto updateBalance(AccountRequestDto accountRequestDto) throws Exception {
        CardEntity cardEntity = cardRepository.findByCardNumber(accountRequestDto.getCardNumber());
        AccountEntity accountEntity = cardEntity.getAccountEntity();
        AccountDto accountDto = new AccountDto();
        synchronized (accountEntity) {
            if (accountEntity.getBalance() + accountRequestDto.getAmount() >= 0) {
                accountEntity.setBalance(accountEntity.getBalance() + accountRequestDto.getAmount());
                accountRepository.saveAndFlush(accountEntity);
                accountDto.setAccountNumber(accountEntity.getAccountNumber());
                accountDto.setBalance(accountEntity.getBalance());
            } else {
                throw new Exception(ErrorConstants.AccountMessage.NOT_ENOUGH_BALANCE_MSG);
            }
        }
        return accountDto;
    }
}
