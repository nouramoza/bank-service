package com.egs.bankservice.service.impl;

import com.egs.bankservice.entity.CardEntity;
import com.egs.bankservice.repository.CardRepository;
import com.egs.bankservice.service.BankService;
import com.egs.bankservice.util.ConstantsUtil;
import com.egs.bankservice.util.ObjectMapperUtils;
import com.egs.bankservice.web.dto.BankRestResponse;
import com.egs.bankservice.web.dto.CardDto;
import com.egs.bankservice.web.error.ErrorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

/**
 * Service Implementation for Bank-Service .
 */

@Service
public class BankServiceImpl implements BankService {

    public static final String VERIFICATION_KEY = "VerificationKey";

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private CardRepository cardRepository;

    @Override
    public BankRestResponse<CardDto> getCardVerification(CardDto cardDto) {
        BankRestResponse<CardDto> restResponse;
        try {
            CardEntity cardEntity = cardRepository.findByCardNumber(cardDto.getCardNumber());
            restResponse = cardEntity != null ?
                    generateResponse(cardEntity) :
                    new BankRestResponse<>(BankRestResponse.STATUS.FAILURE, ErrorConstants.CardVerificationMessage.CARD_NOT_FOUND_MSG, cardDto);
        } catch (Exception e) {
            restResponse = new BankRestResponse<>(BankRestResponse.STATUS.FAILURE, Arrays.toString(e.getStackTrace()), cardDto);
        }
        return restResponse;
    }

    private BankRestResponse<CardDto> generateResponse(CardEntity cardEntity) {
        BankRestResponse<CardDto> restResponse;
        CardDto resultCardDto = ObjectMapperUtils.map(cardEntity, CardDto.class);
        if (Boolean.TRUE.equals(cardEntity.getIsActive()) && !cardEntity.getExpireDate().before(new Date())) {
            httpSession.setAttribute(VERIFICATION_KEY, resultCardDto);
            restResponse = new BankRestResponse<>(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.ResponseMessage.CARD_ACCEPTED,
                    resultCardDto);
        } else {
            restResponse = generateResponseForInactiveCard(cardEntity, resultCardDto);
        }
        return restResponse;
    }

    private BankRestResponse<CardDto> generateResponseForInactiveCard(CardEntity cardEntity, CardDto resultCardDto) {
        BankRestResponse<CardDto> restResponse;
        if (Boolean.FALSE.equals(cardEntity.getIsActive())) {
            if (cardEntity.getIncorrectPinCount() >= ConstantsUtil.CommonValues.WRONG_PIN_COUNTS_TO_BLOCK) {
                restResponse = new BankRestResponse<>(BankRestResponse.STATUS.FAILURE, ErrorConstants.CardVerificationMessage.CARD_IS_BLOCKED_MSG, resultCardDto);
            } else {
                restResponse = new BankRestResponse<>(BankRestResponse.STATUS.FAILURE, ErrorConstants.CardVerificationMessage.CARD_NOT_ACTIVE_MSG, resultCardDto);
            }
        } else if (cardEntity.getExpireDate().before(new Date())) {
            restResponse = new BankRestResponse<>(BankRestResponse.STATUS.FAILURE, ErrorConstants.CardVerificationMessage.CARD_EXPIRED_MSG, resultCardDto);
        } else {
            restResponse = new BankRestResponse<>(BankRestResponse.STATUS.FAILURE, ErrorConstants.CardVerificationMessage.CARD_NOT_VALID_MSG, resultCardDto);
        }
        return restResponse;
    }

    @Override
    public BankRestResponse<CardDto> getCardPinVerification(CardDto cardDto) {
        BankRestResponse<CardDto> restResponse;
        try {
            restResponse = getCardVerification(cardDto);
            if (restResponse.getStatus() == BankRestResponse.STATUS.SUCCESS) {

                CardEntity cardEntity = cardRepository.findByCardNumber(cardDto.getCardNumber());
                if (cardEntity != null) {
                    if (cardEntity.getPin().equals(cardDto.getPin())) {
                        cardEntity.setIncorrectPinCount(0);
                        cardRepository.saveAndFlush(cardEntity);
                        CardDto resultCardDto = ObjectMapperUtils.map(cardEntity, CardDto.class);
                        httpSession.setAttribute(VERIFICATION_KEY, cardDto);
                        restResponse = new BankRestResponse<>(BankRestResponse.STATUS.SUCCESS, ConstantsUtil.ResponseMessage.PIN_ACCEPTED, resultCardDto);
                    } else {
                        cardEntity.setIncorrectPinCount(cardEntity.getIncorrectPinCount() + 1);
                        if (cardEntity.getIncorrectPinCount() == ConstantsUtil.CommonValues.WRONG_PIN_COUNTS_TO_BLOCK) {
                            cardEntity.setIsActive(false);
                            CardDto resultCardDto = ObjectMapperUtils.map(cardEntity, CardDto.class);
                            restResponse = new BankRestResponse<>(BankRestResponse.STATUS.FAILURE, ConstantsUtil.ResponseMessage.CARD_BLOCKED, resultCardDto);
                        } else {
                            CardDto resultCardDto = ObjectMapperUtils.map(cardEntity, CardDto.class);
                            restResponse = new BankRestResponse<>(BankRestResponse.STATUS.FAILURE, ErrorConstants.CardVerificationMessage.WRONG_PIN_MSG, resultCardDto);
                        }
                        cardRepository.saveAndFlush(cardEntity);
                    }
                }
            } else {
                return restResponse;
            }
        } catch (Exception e) {
            restResponse = new BankRestResponse<>(BankRestResponse.STATUS.FAILURE, Arrays.toString(e.getStackTrace()));

        }
        return restResponse;
    }
}