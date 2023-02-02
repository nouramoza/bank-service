package com.egs.bankservice.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ConstantsUtil {

    public static class SessionKey {
        public static final String CARD_NUMBER = "cardNumber";
        public static final String ERROR = "error";
    }

    public static class CommonMessage {
        public static final String YOUR_BALANCE = "Your Balance is: ";
        public static final String SUCCESS_DEPOSIT = "Deposit Done Successful. Your new Balance is: ";
        public static final String SUCCESS_WITHDRAW = "Withdraw Done Successful. Your new Balance is: ";
        public static final String SUCCESS_RECEIPT = "Your Receipt is ready: ";
    }

    public static class ResponseMessage {
        public static final String CARD_ACCEPTED = "Card Number Is Valid.";
        public static final String PIN_ACCEPTED = "Pin Number Is Valid.";
        public static final String INACTIVE_CARD = "Card is Not Active";
        public static final String EXPIRED_CARD = "Card Is Expired";
        public static final String CARD_BLOCKED = "Card Blocked Due To Wrong PIN entered 3 Times.";
    }

    public static class CommonValues {
        public static final int WRONG_PIN_COUNTS_TO_BLOCK = 3;

    }
}