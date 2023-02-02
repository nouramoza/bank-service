package com.egs.bankservice.web.dto;

import com.egs.bankservice.enums.RequestTypeEnum;
import com.sun.istack.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class TransactionLogDto {
    @NotNull
    private String accountNumber;
    @NotNull
    private RequestTypeEnum requestTypeEnum;
    @NotNull
    private Long newBalance;
    @NotNull
    private String response;
    @NotNull
    private int status;
    @NotNull
    private Date requestDate;
    public String description;
}
