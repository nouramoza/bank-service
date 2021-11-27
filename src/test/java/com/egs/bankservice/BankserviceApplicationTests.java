package com.egs.bankservice;

import com.egs.bankservice.web.dto.AccountDto;
import com.egs.bankservice.web.dto.CardDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.net.URI;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BankserviceApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	private static final URI ADD_ACCOUNT_URI = URI.create("/api/v1/basic-info/addAccount");
	private static final URI ADD_CARD_URI = URI.create("/api/v1/basic-info/addCard");

	@Test
	public void addAccount() throws Exception {
		AccountDto accountDto = new AccountDto(
				null, "1235430001", 1L, 1000L,  true);
		String accountStr = mapToJson(accountDto);
		RequestBuilder req = post(ADD_ACCOUNT_URI)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(accountStr);

        MvcResult mvcResult = this.mockMvc.perform(req)
//				.andExpect(content().string(containsString(outputExpectedMockBoard)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        accountDto = new AccountDto(
				null, "1235430002", 2L, 0L,  true);
		accountStr = mapToJson(accountDto);
		req = post(ADD_ACCOUNT_URI)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(accountStr);

        mvcResult = this.mockMvc.perform(req)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

        accountDto = new AccountDto(
				null, "1235430003", 3L, 5000L,  true);
		accountStr = mapToJson(accountDto);
		req = post(ADD_ACCOUNT_URI)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(accountStr);

        mvcResult = this.mockMvc.perform(req)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

	}

	@Test
	public void addCard() throws Exception {
		AccountDto accountDto = new AccountDto();
		accountDto.setAccountNumber("1235430001");
		CardDto cardDto = new CardDto("6280231451904303", 123, new Date(),
				"1234", true, 0, accountDto);

		String cardStr = mapToJson(cardDto);
		RequestBuilder req = post(ADD_CARD_URI)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(cardStr);

		MvcResult mvcResult = this.mockMvc.perform(req)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		accountDto = new AccountDto();
		accountDto.setAccountNumber("1235430002");
		cardDto = new CardDto("6280231451904304", 321, new Date(),
				"1234", false, 0, accountDto);

		cardStr = mapToJson(cardDto);
		req = post(ADD_CARD_URI)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(cardStr);

		mvcResult = this.mockMvc.perform(req)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		accountDto = new AccountDto();
		accountDto.setAccountNumber("1235430003");
		cardDto = new CardDto("6280231451904305", 199, new Date(),
				"9876", false, 0, accountDto);

		cardStr = mapToJson(cardDto);
		req = post(ADD_CARD_URI)
				.contentType(MediaType.APPLICATION_JSON) // for DTO
				.content(cardStr);

		mvcResult = this.mockMvc.perform(req)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

	}

	protected static String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}


}
