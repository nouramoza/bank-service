package com.egs.bankservice.web.dto;

import com.egs.bankservice.enums.RequestTypeEnum;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AccountRequestDto {

    @NonNull
    private RequestTypeEnum requestType;

    private String cardNumber;

    private String description;

    private Long amount;

    private Date fromDate;

    private Date toDate;
}
