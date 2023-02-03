package com.egs.bankservice.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto implements Serializable {
    private List<CardDto> cardDtoList;

    @NonNull
    private String accountNumber;

    @NonNull
    private Long personId;

    @NonNull
    private Long balance;

    protected LocalDateTime openDate;

    protected LocalDateTime closeDate;

    private Boolean isActive;

    private List<TransactionLogDto> transactionList;

}
