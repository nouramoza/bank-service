package com.egs.bankservice.web.api;

import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
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
class BasicInfoResourceApiTests {

	@Autowired
	MockMvc mockMvc;

	private static final URI ADD_ACCOUNT_URI = URI.create("/basic-info/addAccount");
	private static final URI ADD_CARD_URI = URI.create("/basic-info/addCard");

	@Test
	@Order(2)
	void addAccount() throws Exception {
		AccountDto accountDto = new AccountDto();
		accountDto.setAccountNumber(generateRandomAccountNumber());
		accountDto.setPersonId(1L);
		accountDto.setBalance(1000L);
		accountDto.setIsActive(true);
		String accountStr = mapToJson(accountDto);
		RequestBuilder req = post(ADD_ACCOUNT_URI)
				.header("Authorization", MOCK_TOKEN)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(accountStr);

        MvcResult mvcResult = this.mockMvc.perform(req)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
		BankRestResponse bankRestResponse =new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
		Assertions.assertEquals(0, bankRestResponse.getStatus());

        accountDto = new AccountDto();
		accountDto.setAccountNumber(generateRandomAccountNumber());
		accountDto.setPersonId(2L);
		accountDto.setBalance(0L);
		accountDto.setIsActive(true);
		accountStr = mapToJson(accountDto);
		req = post(ADD_ACCOUNT_URI)
				.header("Authorization", MOCK_TOKEN)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(accountStr);

        mvcResult = this.mockMvc.perform(req)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();


		bankRestResponse =new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
		Assertions.assertEquals(0, bankRestResponse.getStatus());


		accountDto = new AccountDto();
		accountDto.setAccountNumber("1235430003");
		accountDto.setPersonId(3L);
		accountDto.setBalance(5000L);
		accountDto.setIsActive(true);

		accountStr = mapToJson(accountDto);
		req = post(ADD_ACCOUNT_URI)
				.header("Authorization", MOCK_TOKEN)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(accountStr);

        mvcResult = this.mockMvc.perform(req)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		bankRestResponse =new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
		Assertions.assertEquals(0, bankRestResponse.getStatus());
	}

	@Test
	@Order(1)
	void addCard() throws Exception {
		AccountDto accountDto = new AccountDto();
		accountDto.setAccountNumber(generateRandomAccountNumber());
		accountDto.setPersonId(1L);
		accountDto.setBalance(1000L);
		accountDto.setIsActive(true);
		String accountStr = mapToJson(accountDto);
		RequestBuilder req = post(ADD_ACCOUNT_URI)
				.header("Authorization", MOCK_TOKEN)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(accountStr);

		MvcResult mvcResult = this.mockMvc.perform(req)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		BankRestResponse bankRestResponse =new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
		Assertions.assertEquals(0, bankRestResponse.getStatus());
		CardDto cardDto = new CardDto(generateRandomCardNumber(), 123, generateDate("2024-01-01","yyyy-MM-dd"),
				generateDefaultDate(),
				"1234", true, 0, accountDto);

		String cardStr = mapToJson(cardDto);
		RequestBuilder cardReq = post(ADD_CARD_URI)
				.header("Authorization", MOCK_TOKEN)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(cardStr);

		MvcResult cardMvcResult = this.mockMvc.perform(cardReq)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		BankRestResponse cardBankRestResponse =new ObjectMapper().readValue(cardMvcResult.getResponse().getContentAsString(), BankRestResponse.class);
		Assertions.assertEquals(0, cardBankRestResponse.getStatus());
	}
}
