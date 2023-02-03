package com.egs.bankservice.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class CardDto implements Serializable {
    @NonNull
    @Setter
    private String cardNumber;
    @Setter
    private int cvv2;
    @Setter
    private Date expireDate;
    private String pin;
    @Setter
    private Boolean isActive;
    private int incorrectPinCount;
    private AccountDto accountDto;

}
