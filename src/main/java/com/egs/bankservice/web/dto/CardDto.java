package com.egs.bankservice.web.dto;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class CardDto implements Serializable {
    @NonNull
    private String cardNumber;
    private int cvv2;
    private Date expireDate;
    private String pin;
    private Boolean isActive;
    private int incorrectPinCount;
    private AccountDto accountDto;

}
