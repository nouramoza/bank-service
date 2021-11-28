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
    public RequestTypeEnum requestType;

    public String cardNumber;

    public String description;

    public Long amount;

    public Date fromDate;

    public Date toDate;
}
