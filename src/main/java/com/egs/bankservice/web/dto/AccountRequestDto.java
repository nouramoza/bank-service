package com.egs.bankservice.web.dto;

import com.egs.bankservice.enums.RequestTypeEnum;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AccountRequestDto {

    @NonNull
    public RequestTypeEnum requestType;

    @NonNull
    public String cardNumber;

    public Long amount;
}
