package com.egs.bankservice.service;

import com.egs.bankservice.web.dto.AccountRequestDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    public BankRestResponse requestManagement(AccountRequestDto accountRequestDto);
}
