package com.egs.bankservice.web.dto;

import com.sun.istack.internal.NotNull;
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

    @NotNull
    private Long personId;

    @NotNull
    private Long balance;

    private Boolean isActive;

    private List<TransactionLogDto> transactionList;

}
