package com.egs.bankservice.enums;

public enum RequestTypeEnum {
    CHECK_BALANCE(1),
    DEPOSIT(2),
    WITHDRAW(3),
    GET_RECEIPT(4);

    private int requestType;

    RequestTypeEnum(int requestType) {
        this.requestType = requestType;
    }

    public int getValue() {
        return requestType;
    }
}
