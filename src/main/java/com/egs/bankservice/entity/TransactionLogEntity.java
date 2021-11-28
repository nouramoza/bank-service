package com.egs.bankservice.entity;

import com.egs.bankservice.enums.RequestTypeEnum;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION_LOG")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class TransactionLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ACCOUNT_ID", referencedColumnName = "ID", nullable=false)
    private AccountEntity accountEntity;

    @Column(name = "REQUEST_TYPE", nullable=false)
    @NotNull
    private RequestTypeEnum requestTypeEnum;

    @Column(name = "NEW_BALANCE", nullable=false)
    @NotNull
    private Long newBalance;

    @Column(name = "RESPONSE", nullable=false)
    @NotNull
    private String response;

    @Column(name = "STATUS", nullable=false)
    @NotNull
    private int status;

    @Column(name = "REQUEST_DATE", nullable=false)
    @NonNull
    private Date requestDate;

    @Column(name = "DESCRIPTION", nullable=false)
    private String description;
}
