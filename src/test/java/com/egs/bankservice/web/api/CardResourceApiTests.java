package com.egs.bankservice.web.api;

import com.egs.bankservice.util.ConstantsUtil;
import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import com.egs.bankservice.web.error.ErrorConstants;
import com.egs.bankservice.web.helper.AccountHelper;
import com.egs.bankservice.web.helper.CardHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.net.URI;

import static com.egs.bankservice.web.helper.CommonMethodHelper.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CardResourceApiTests {

    @Autowired
    MockMvc mockMvc;

    private static final URI VALIDATE_CARD_URI = URI.create("/card-service/validateCardNumber");
    private static final URI VALIDATE_CARD_PIN_URI = URI.create("/card-service/validateCardPinNumber");

    @Test
    void validateCard_OK() throws Exception {

        AccountDto accountDto = AccountHelper.addAccount(mockMvc, generateRandomAccountNumber());
        CardDto cardDto = CardHelper.addValidCard(mockMvc, accountDto, generateRandomCardNumber());
        String cardStr = mapToJson(cardDto);

        RequestBuilder req = post(VALIDATE_CARD_URI)
                .header("Authorization", MOCK_TOKEN)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .content(cardStr);
        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(ConstantsUtil.ResponseMessage.CARD_ACCEPTED, bankRestResponse.getMessage());
    }

    @Test
    void validateCard_expired() throws Exception {
        AccountDto accountDto = AccountHelper.addAccount(mockMvc, generateRandomAccountNumber());
        CardDto cardDto = CardHelper.addExpiredCard(mockMvc, accountDto, generateRandomCardNumber());

        String cardStr = mapToJson(cardDto);

        RequestBuilder req = post(VALIDATE_CARD_URI)
                .header("Authorization", MOCK_TOKEN)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .content(cardStr);
        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(BankRestResponse.STATUS.FAILURE, bankRestResponse.getStatus());
        Assertions.assertEquals(ErrorConstants.CardVerificationMessage.CARD_EXPIRED_MSG, bankRestResponse.getMessage());
    }

    @Test
    void validateCard_notActive() throws Exception {
        AccountDto accountDto = AccountHelper.addAccount(mockMvc, generateRandomAccountNumber());
        CardDto cardDto = CardHelper.addInactiveCard(mockMvc, accountDto, generateRandomCardNumber());

        String cardStr = mapToJson(cardDto);

        RequestBuilder req = post(VALIDATE_CARD_URI)
                .header("Authorization", MOCK_TOKEN)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .content(cardStr);
        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(BankRestResponse.STATUS.FAILURE, bankRestResponse.getStatus());
        Assertions.assertEquals(ErrorConstants.CardVerificationMessage.CARD_NOT_ACTIVE_MSG, bankRestResponse.getMessage());
    }

    @Test
    void validateCard_blocked() throws Exception {
        AccountDto accountDto = AccountHelper.addAccount(mockMvc, generateRandomAccountNumber());
        CardDto cardDto = CardHelper.addBlockedCard(mockMvc, accountDto, generateRandomCardNumber());

        String cardStr = mapToJson(cardDto);

        RequestBuilder req = post(VALIDATE_CARD_URI)
                .header("Authorization", MOCK_TOKEN)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .content(cardStr);
        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(BankRestResponse.STATUS.FAILURE, bankRestResponse.getStatus());
        Assertions.assertEquals(ErrorConstants.CardVerificationMessage.CARD_IS_BLOCKED_MSG, bankRestResponse.getMessage());
    }

    @Test
    void validateCardPin_ok() throws Exception {
        AccountDto accountDto = AccountHelper.addAccount(mockMvc, generateRandomAccountNumber());
        CardDto cardDto = CardHelper.addValidCard(mockMvc, accountDto, generateRandomCardNumber());

        String cardStr = mapToJson(cardDto);

        RequestBuilder req = post(VALIDATE_CARD_PIN_URI)
                .header("Authorization", MOCK_TOKEN)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .content(cardStr);
        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(BankRestResponse.STATUS.SUCCESS, bankRestResponse.getStatus());
        Assertions.assertEquals(ConstantsUtil.ResponseMessage.PIN_ACCEPTED, bankRestResponse.getMessage());
    }

    @Test
    void validateCardPin_nok() throws Exception {
        AccountDto accountDto = AccountHelper.addAccount(mockMvc, generateRandomAccountNumber());
        CardDto cardDto = CardHelper.addValidCard(mockMvc, accountDto, generateRandomCardNumber());
        cardDto.setPin("6598");
        String cardStr = mapToJson(cardDto);

        RequestBuilder req = post(VALIDATE_CARD_PIN_URI)
                .header("Authorization", MOCK_TOKEN)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .content(cardStr);
        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(BankRestResponse.STATUS.FAILURE, bankRestResponse.getStatus());
        Assertions.assertEquals(ErrorConstants.CardVerificationMessage.WRONG_PIN_MSG, bankRestResponse.getMessage());
    }

   @Test
    void blockCardBuExceedWrongPin() throws Exception {
        AccountDto accountDto = AccountHelper.addAccount(mockMvc, generateRandomAccountNumber());
        CardDto cardDto = CardHelper.addValidCard(mockMvc, accountDto, generateRandomCardNumber());
        cardDto.setPin("6598");
        String cardStr = mapToJson(cardDto);

        RequestBuilder req = post(VALIDATE_CARD_PIN_URI)
                .header("Authorization", MOCK_TOKEN)
                .contentType(MediaType.APPLICATION_JSON) // for DTO
                .content(cardStr);

        //1
        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(BankRestResponse.STATUS.FAILURE, bankRestResponse.getStatus());
        Assertions.assertEquals(ErrorConstants.CardVerificationMessage.WRONG_PIN_MSG, bankRestResponse.getMessage());

       //2
        mvcResult = this.mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(BankRestResponse.STATUS.FAILURE, bankRestResponse.getStatus());
        Assertions.assertEquals(ErrorConstants.CardVerificationMessage.WRONG_PIN_MSG, bankRestResponse.getMessage());

       //3
       mvcResult = this.mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(BankRestResponse.STATUS.FAILURE, bankRestResponse.getStatus());
        Assertions.assertEquals(ConstantsUtil.ResponseMessage.CARD_BLOCKED, bankRestResponse.getMessage());

       //4
       mvcResult = this.mockMvc.perform(req)
               .andExpect(status().isOk())
               .andDo(print())
               .andReturn();
       bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
       Assertions.assertEquals(BankRestResponse.STATUS.FAILURE, bankRestResponse.getStatus());
       Assertions.assertEquals(ErrorConstants.CardVerificationMessage.CARD_IS_BLOCKED_MSG, bankRestResponse.getMessage());
   }

}
