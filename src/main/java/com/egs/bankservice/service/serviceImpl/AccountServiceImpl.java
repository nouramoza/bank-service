package com.egs.bankservice.service.serviceImpl;

//import com.egs.bankservice.entity.AccountEntity;
//import com.egs.bankservice.entity.CardEntity;
//import com.egs.bankservice.entity.TransactionLogEntity;
import com.egs.bankservice.enums.RequestTypeEnum;
//import com.egs.bankservice.repository.AccountRepository;
//import com.egs.bankservice.repository.CardRepository;
//import com.egs.bankservice.repository.TransactionLogRepository;
import com.egs.bankservice.service.AccountService;
import com.egs.bankservice.util.ConstantsUtil;
import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.AccountRequestDto;
import com.egs.bankservice.web.dto.BankRestResponse;
//import com.egs.bankservice.web.dto.TransactionLogDto;
//import com.egs.bankservice.web.error.ErrorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    public static final String VERIFICATION_KEY = "VerificationKey";

    @Autowired
    private HttpSession httpSession;

//    private TransactionLogRepository transactionLogRepository;
//    private AccountRepository accountRepository;
//    private CardRepository cardRepository;

//    public AccountServiceImpl(AccountRepository accountRepository, CardRepository cardRepository, TransactionLogRepository transactionLogRepository) {
//        this.transactionLogRepository = transactionLogRepository;
//        this.accountRepository = accountRepository;
//        this.cardRepository = cardRepository;
//    }

    @Override
    public BankRestResponse requestManagement(AccountRequestDto accountRequestDto) {
        BankRestResponse restResponse = null;
//        CardEntity cardEntity = cardRepository.findByCardNumber(accountRequestDto.getCardNumber());
//        AccountEntity accountEntity = cardEntity.getAccountEntity();
//        TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
//        transactionLogEntity.setRequestDate(new Date());
//        transactionLogEntity.setAccountEntity(accountEntity);
//        transactionLogEntity.setRequestTypeEnum(accountRequestDto.getRequestType());

        try {
            AccountDto accountDto = null;
            if (accountRequestDto.getRequestType().equals(RequestTypeEnum.CHECK_BALANCE)) {
//                accountDto = checkBalance(accountRequestDto, accountEntity);
                restResponse = new BankRestResponse(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.CommonMessage.YOUR_BALANCE + accountDto.getBalance());
            } else if (accountRequestDto.getRequestType().equals(RequestTypeEnum.DEPOSIT)) {
//                accountDto = updateBalance(accountRequestDto, accountEntity);
                restResponse = new BankRestResponse(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.CommonMessage.SUCCESS_DEPOSIT + accountDto.getBalance());

            } else if (accountRequestDto.getRequestType().equals(RequestTypeEnum.WITHDRAW)) {
                accountRequestDto.setAmount(-accountRequestDto.getAmount());
//                accountDto = updateBalance(accountRequestDto, accountEntity);
                restResponse = new BankRestResponse(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.CommonMessage.SUCCESS_WITHDRAW + accountDto.getBalance());
            } else if (accountRequestDto.getRequestType().equals(RequestTypeEnum.GET_RECEIPT)) {
//                accountDto = getReceipt(accountRequestDto, accountEntity);
                restResponse = new BankRestResponse(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.CommonMessage.SUCCESS_RECEIPT + accountDto.getTransactionList());
            }
        } catch (Exception e) {
            httpSession.setAttribute(VERIFICATION_KEY, ConstantsUtil.SessionKey.ERROR);
            restResponse = new BankRestResponse(BankRestResponse.STATUS.FAILURE, e.toString());
        }


//        transactionLogEntity.setNewBalance(accountEntity.getBalance());
//        transactionLogEntity.setResponse(restResponse.getMessage());
//        transactionLogEntity.setStatus(restResponse.getStatus());
//        transactionLogRepository.saveAndFlush(transactionLogEntity);
        return restResponse;
    }

//    private AccountDto checkBalance(AccountRequestDto accountRequestDto, AccountEntity accountEntity) {
//
//        AccountDto accountDto = new AccountDto();
//        accountDto.setAccountNumber(accountEntity.getAccountNumber());
//        accountDto.setBalance(accountEntity.getBalance());
//        return accountDto;
//    }
//
//    private AccountDto updateBalance(AccountRequestDto accountRequestDto, AccountEntity accountEntity) throws Exception {
//        AccountDto accountDto = new AccountDto();
//        synchronized (accountEntity) {
//            if (accountEntity.getBalance() + accountRequestDto.getAmount() >= 0) {
//                accountEntity.setBalance(accountEntity.getBalance() + accountRequestDto.getAmount());
//                accountRepository.saveAndFlush(accountEntity);
//                accountDto.setAccountNumber(accountEntity.getAccountNumber());
//                accountDto.setBalance(accountEntity.getBalance());
//            } else {
//                throw new Exception(ErrorConstants.AccountMessage.NOT_ENOUGH_BALANCE_MSG);
//            }
//        }
//        return accountDto;
//    }

//    public BankRestResponse getReceipt(AccountRequestDto accountRequestDto) {
//        BankRestResponse restResponse = null;

//        try {
//            CardEntity cardEntity = cardRepository.findByCardNumber(accountRequestDto.getCardNumber());
//            AccountEntity accountEntity = cardEntity.getAccountEntity();
//            List<TransactionLogEntity> transactionLogList = transactionLogRepository.getReceipt(
//                    accountEntity.getAccountNumber(), accountRequestDto.getFromDate(), accountRequestDto.getToDate());
//
//            List<TransactionLogDto> transactionLogDtoList = new ArrayList<>();
//            for (TransactionLogEntity tl : transactionLogList) {
//                TransactionLogDto transactionLogDto = new TransactionLogDto();
//                transactionLogDto.setAccountNumber(tl.getAccountEntity().getAccountNumber());
//                transactionLogDto.setRequestTypeEnum(tl.getRequestTypeEnum());
//                transactionLogDto.setNewBalance(tl.getNewBalance());
//                transactionLogDto.setResponse(tl.getResponse());
//                transactionLogDto.setStatus(tl.getStatus());
//                transactionLogDto.setRequestDate(tl.getRequestDate());
//                transactionLogDto.setDescription(tl.getDescription());
//                transactionLogDtoList.add(transactionLogDto);
//            }
//            restResponse = new BankRestResponse(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.CommonMessage.SUCCESS_WITHDRAW + transactionLogDtoList);
//        } catch (Exception e) {
//            httpSession.setAttribute(VERIFICATION_KEY, ConstantsUtil.SessionKey.ERROR);
//            restResponse = new BankRestResponse(BankRestResponse.STATUS.FAILURE, e.toString());
//        }
//        return restResponse;
//    }
}
