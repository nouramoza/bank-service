package com.egs.bankservice.web.dto;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AccountDto {
    private List<CardDto> cardDtoList;

    @NonNull
    private String accountNumber;

    @NonNull
    private Long personId;

    @NonNull
    private Long balance;

    private Boolean isActive;

    private List<TransactionLogDto> transactionList;

}
