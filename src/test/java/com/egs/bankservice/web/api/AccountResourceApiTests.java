package com.egs.bankservice.web.api;

import com.egs.bankservice.enums.RequestTypeEnum;
import com.egs.bankservice.util.ConstantsUtil;
import com.egs.bankservice.util.ObjectMapperUtils;
import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.AccountRequestDto;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
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
class AccountResourceApiTests {

	@Autowired
	MockMvc mockMvc;

	private static final URI REQUEST_MANAGEMENT_URI = URI.create("/account-service/requestManagement");

	@Test
	void getBalance_OK() throws Exception {

		AccountDto accountDto = AccountHelper.addAccount(mockMvc, generateRandomAccountNumber());
		CardDto cardDto = CardHelper.addValidCard(mockMvc, accountDto, generateRandomCardNumber());

		AccountRequestDto accountRequestDto = new AccountRequestDto();
		accountRequestDto.setRequestType(RequestTypeEnum.CHECK_BALANCE);
		accountRequestDto.setCardNumber(cardDto.getCardNumber());

		String accountRequestStr = mapToJson(accountRequestDto);

		RequestBuilder req = post(REQUEST_MANAGEMENT_URI)
				.header("Authorization", MOCK_TOKEN)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(accountRequestStr);
		MvcResult mvcResult = this.mockMvc.perform(req)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
		Assertions.assertEquals(BankRestResponse.STATUS.SUCCESS, bankRestResponse.getStatus());
		Assertions.assertEquals(ConstantsUtil.CommonMessage.YOUR_BALANCE.concat(accountDto.getBalance().toString()), bankRestResponse.getMessage());
		Assertions.assertEquals(accountDto.getAccountNumber(),
                ObjectMapperUtils.map(bankRestResponse.getData(), AccountDto.class).getAccountNumber());
	}

	@Test
	void deposit_OK() throws Exception {

		AccountDto accountDto = AccountHelper.addAccount(mockMvc, generateRandomAccountNumber());
		CardDto cardDto = CardHelper.addValidCard(mockMvc, accountDto, generateRandomCardNumber());

		AccountRequestDto accountRequestDto = new AccountRequestDto();
		accountRequestDto.setRequestType(RequestTypeEnum.DEPOSIT);
		accountRequestDto.setAmount(2000L);
		accountRequestDto.setCardNumber(cardDto.getCardNumber());

		String accountRequestStr = mapToJson(accountRequestDto);

		RequestBuilder req = post(REQUEST_MANAGEMENT_URI)
				.header("Authorization", MOCK_TOKEN)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(accountRequestStr);
		MvcResult mvcResult = this.mockMvc.perform(req)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		BankRestResponse bankRestResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BankRestResponse.class);
		Assertions.assertEquals(BankRestResponse.STATUS.SUCCESS, bankRestResponse.getStatus());
		Assertions.assertEquals(ConstantsUtil.CommonMessage.SUCCESS_DEPOSIT.concat("3000"), bankRestResponse.getMessage());
		Assertions.assertEquals(accountDto.getAccountNumber(),
                ObjectMapperUtils.map(bankRestResponse.getData(), AccountDto.class).getAccountNumber());
	}

}
