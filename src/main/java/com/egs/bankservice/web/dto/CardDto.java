package com.egs.bankservice.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDto implements Serializable {
    @NonNull
    private String cardNumber;
    private int cvv2;
    private Date expireDate;
    private Date issueDate;
    private String pin;
    private Boolean isActive;
    private int incorrectPinCount;
    private AccountDto accountDto;

}
