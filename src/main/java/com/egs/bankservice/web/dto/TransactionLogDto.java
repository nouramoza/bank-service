package com.egs.bankservice.web.dto;

import com.egs.bankservice.enums.RequestTypeEnum;
import com.sun.istack.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class TransactionLogDto implements Serializable {
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
    private String description;
}
