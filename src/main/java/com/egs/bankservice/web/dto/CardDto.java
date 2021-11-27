package com.egs.bankservice.web.dto;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class CardDto {
    @NonNull
    public String cardNumber;
    public int cvv2;
    public Date expireDate;
    private String pin;
    private Boolean isActive;
    private int incorrectPinCount;
    private AccountDto accountDto;

}
