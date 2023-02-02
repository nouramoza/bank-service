package com.egs.bankservice.web.dto;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class CardDto implements Serializable {
    @NonNull
    public String cardNumber;
    public int cvv2;
    public Date expireDate;
    @Transient
    private String pin;
    private Boolean isActive;
    private int incorrectPinCount;
    private AccountDto accountDto;

}
