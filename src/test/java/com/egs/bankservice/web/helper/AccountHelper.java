package com.egs.bankservice.web.helper;

import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.net.URI;

import static com.egs.bankservice.web.helper.CommonMethodHelper.MOCK_TOKEN;
import static com.egs.bankservice.web.helper.CommonMethodHelper.mapToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountHelper {
    private static final URI ADD_ACCOUNT_URI = URI.create("/basic-info/addAccount");

    public static AccountDto addAccount(MockMvc mockMvc, String accountNo) throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(accountNo);
        accountDto.setPersonId(1L);
        accountDto.setBalance(1000L);
        accountDto.setIsActive(true);
        String accountStr = mapToJson(accountDto);
        RequestBuilder req = post(ADD_ACCOUNT_URI)
                .header("Authorization", MOCK_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountStr);

        MvcResult mvcResult = mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(0, bankRestResponse.getStatus());
        return accountDto;
    }

//    public static AccountDto getAccount(MockMvc mockMvc, String accountNo) throws Exception {
//        AccountDto accountDto = new AccountDto();
//        accountDto.setAccountNumber(accountNo);
//        accountDto.setPersonId(1L);
//        accountDto.setBalance(1000L);
//        accountDto.setIsActive(true);
//        String accountStr = mapToJson(accountDto);
//        RequestBuilder req = post(ADD_ACCOUNT_URI)
//                .header("Authorization", MOCK_TOKEN)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(accountStr);
//
//        MvcResult mvcResult = mockMvc.perform(req)
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//        BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
//        Assertions.assertEquals(0, bankRestResponse.getStatus());
//    }
}
