package com.egs.bankservice.web.helper;

import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.net.URI;

import static com.egs.bankservice.web.helper.CommonMethodHelper.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CardHelper {
    private static final URI ADD_CARD_URI = URI.create("/basic-info/addCard");
    private static final int CVV = 199;
    private static final String PIN = "1234";
    public static CardDto addValidCard(MockMvc mockMvc, AccountDto accountDto, String cardNumber) throws Exception {
        CardDto cardDto = new CardDto(cardNumber, CVV, generateDate("2024-01-01","yyyy-MM-dd"),
                generateDate("2021-01-01","yyyy-MM-dd"),
                PIN, true, 0, accountDto);
        return performCardInsertion(mockMvc, cardDto);
    }

    public static CardDto addExpiredCard(MockMvc mockMvc,AccountDto accountDto, String cardNumber) throws Exception {
        CardDto cardDto = new CardDto(cardNumber, CVV, generateDate("2023-01-01","yyyy-MM-dd"),
                generateDate("2021-01-01","yyyy-MM-dd"),
                PIN, true, 0, accountDto);
        return performCardInsertion(mockMvc, cardDto);
    }

    public static CardDto addInactiveCard(MockMvc mockMvc,AccountDto accountDto, String cardNumber) throws Exception {
        CardDto cardDto = new CardDto(cardNumber, CVV, generateDate("2024-01-01","yyyy-MM-dd"),
                generateDate("2021-01-01","yyyy-MM-dd"),
                PIN, false, 0, accountDto);
        return performCardInsertion(mockMvc, cardDto);
    }

    public static CardDto addBlockedCard(MockMvc mockMvc,AccountDto accountDto, String cardNumber) throws Exception {
        CardDto cardDto = new CardDto(cardNumber, CVV, generateDate("2024-01-01","yyyy-MM-dd"),
                generateDate("2021-01-01","yyyy-MM-dd"),
                PIN, false, 3, accountDto);
        return performCardInsertion(mockMvc, cardDto);
    }

    private static CardDto performCardInsertion(MockMvc mockMvc, CardDto cardDto) throws Exception {
        String cardStr = mapToJson(cardDto);
        RequestBuilder req = post(ADD_CARD_URI)
                .header("Authorization", MOCK_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardStr);

        MvcResult mvcResult = mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
        Assertions.assertEquals(0, bankRestResponse.getStatus());
        return cardDto;
    }
}
