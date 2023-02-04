package com.egs.bankservice.service.impl;

import com.egs.bankservice.entity.AccountEntity;
import com.egs.bankservice.entity.CardEntity;
import com.egs.bankservice.entity.TransactionLogEntity;
import com.egs.bankservice.enums.RequestTypeEnum;
import com.egs.bankservice.repository.AccountRepository;
import com.egs.bankservice.repository.CardRepository;
import com.egs.bankservice.repository.TransactionLogRepository;
import com.egs.bankservice.service.AccountService;
import com.egs.bankservice.util.ConstantsUtil;
import com.egs.bankservice.util.ObjectMapperUtils;
import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.AccountRequestDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.error.ErrorConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    public static final String VERIFICATION_KEY = "VerificationKey";

    private TransactionLogRepository transactionLogRepository;
    private AccountRepository accountRepository;
    private CardRepository cardRepository;

    public AccountServiceImpl(AccountRepository accountRepository, CardRepository cardRepository, TransactionLogRepository transactionLogRepository) {
        this.transactionLogRepository = transactionLogRepository;
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public BankRestResponse requestManagement(AccountRequestDto accountRequestDto) {
        BankRestResponse restResponse = new BankRestResponse();
        CardEntity cardEntity = cardRepository.findByCardNumber(accountRequestDto.getCardNumber());
        AccountEntity accountEntity = cardEntity.getAccountEntity();
        TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
        transactionLogEntity.setRequestDate(new Date());
        transactionLogEntity.setAccountEntity(accountEntity);
        transactionLogEntity.setRequestTypeEnum(accountRequestDto.getRequestType());

        try {
            if (accountRequestDto.getRequestType().equals(RequestTypeEnum.CHECK_BALANCE)) {
                restResponse = checkBalance(accountEntity);
                transactionLogEntity.setDescription(accountRequestDto.getRequestType().name());
            } else if (accountRequestDto.getRequestType().equals(RequestTypeEnum.DEPOSIT)) {
                restResponse = updateBalance(accountRequestDto, accountEntity);
                transactionLogEntity.setDescription(accountRequestDto.getRequestType().name().concat("-")
                        .concat(accountRequestDto.getDescription() != null ? accountRequestDto.getDescription() : accountRequestDto.getAmount().toString()));

            } else if (accountRequestDto.getRequestType().equals(RequestTypeEnum.WITHDRAW)) {
                accountRequestDto.setAmount(-accountRequestDto.getAmount());
                restResponse = updateBalance(accountRequestDto, accountEntity);
                transactionLogEntity.setDescription(accountRequestDto.getRequestType().name().concat("-")
                        .concat(accountRequestDto.getDescription() != null ? accountRequestDto.getDescription() : accountRequestDto.getAmount().toString()));
            } else if (accountRequestDto.getRequestType().equals(RequestTypeEnum.GET_RECEIPT)) {
                restResponse = getReceipt(accountRequestDto);
                transactionLogEntity.setDescription(accountRequestDto.getRequestType().name());
            }
        } catch (Exception e) {
            restResponse = new BankRestResponse(BankRestResponse.STATUS.FAILURE, e.getMessage());
        }

        transactionLogEntity.setNewBalance(accountEntity.getBalance());
        String message = restResponse.getMessage();
        if (!message.isEmpty()) {
            transactionLogEntity.setResponse(message.length() > 255 ? message.substring(0, 255) : message);
        }
        transactionLogEntity.setStatus(restResponse.getStatus());
        transactionLogRepository.saveAndFlush(transactionLogEntity);
        return restResponse;
    }

    private BankRestResponse checkBalance(AccountEntity accountEntity) {
        AccountDto accountDto = ObjectMapperUtils.map(accountEntity, AccountDto.class);
        return new BankRestResponse(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.CommonMessage.YOUR_BALANCE + accountEntity.getBalance(), accountDto);
    }

    private BankRestResponse updateBalance(AccountRequestDto accountRequestDto, AccountEntity accountEntity) {
        BankRestResponse<AccountDto> restResponse;

        if (accountEntity.getBalance() + accountRequestDto.getAmount() >= 0) {
            accountEntity.setBalance(accountEntity.getBalance() + accountRequestDto.getAmount());
            accountRepository.saveAndFlush(accountEntity);
            AccountDto accountDto = ObjectMapperUtils.map(accountEntity, AccountDto.class);
            restResponse = new BankRestResponse<>(BankRestResponse.STATUS.SUCCESS,
                    (accountRequestDto.getAmount() > 0 ?
                            ConstantsUtil.CommonMessage.SUCCESS_DEPOSIT :
                            ConstantsUtil.CommonMessage.SUCCESS_WITHDRAW) +
                            accountEntity.getBalance(), accountDto);

        } else {
            AccountDto accountDto = ObjectMapperUtils.map(accountEntity, AccountDto.class);
            restResponse = new BankRestResponse<>(BankRestResponse.STATUS.FAILURE, ErrorConstants.AccountMessage.NOT_ENOUGH_BALANCE_MSG, accountDto);
        }
        return restResponse;
    }

    public BankRestResponse getReceipt(AccountRequestDto accountRequestDto) {
        BankRestResponse restResponse = null;

        try {
            CardEntity cardEntity = cardRepository.findByCardNumber(accountRequestDto.getCardNumber());
            AccountEntity accountEntity = cardEntity.getAccountEntity();
            List<RequestTypeEnum> requestTypeEnums = new ArrayList<>();
            requestTypeEnums.add(RequestTypeEnum.DEPOSIT);
            requestTypeEnums.add(RequestTypeEnum.WITHDRAW);
            List<TransactionLogEntity> transactionLogList = transactionLogRepository.getReceipt(
                    accountEntity.getAccountNumber(), accountRequestDto.getFromDate(), accountRequestDto.getToDate(), requestTypeEnums);

            AccountDto accountDto = ObjectMapperUtils.map(accountEntity, AccountDto.class);
            final String[] logResult = {""};
            transactionLogList
                    .forEach(tl -> logResult[0] = logResult[0].concat(tl.getRequestDate() + " - " + tl.getRequestTypeEnum() + " - " + tl.getNewBalance() +
                            " - " + tl.getDescription() + "////"));
            restResponse = new BankRestResponse(BankRestResponse.STATUS.SUCCESS,
                    ConstantsUtil.CommonMessage.SUCCESS_RECEIPT.concat(logResult[0]), accountDto);
        } catch (Exception e) {
            restResponse = new BankRestResponse(BankRestResponse.STATUS.FAILURE, Arrays.toString(e.getStackTrace()));
        }
        return restResponse;
    }
}