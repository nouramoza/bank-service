package com.egs.bankservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "CARD", uniqueConstraints = {@UniqueConstraint(columnNames = {"CARD_NUMBER"})})
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
    private AccountEntity accountEntity;

    @Column(name = "CARD_NUMBER", nullable = false, unique = true)
    @NonNull
    private String cardNumber;

    @Column(name = "CVV2", nullable = false)
    @NonNull
    private int cvv2;

    @Column(name = "ISSUE_DATE", nullable = false)
    @NonNull
    private LocalDateTime issueDate;

    @Column(name = "EXPIRE_DATE", nullable = false)
    @NonNull
    private Date expireDate;

    @Column(name = "PIN", nullable = false)
    private String pin;

    @Column(name = "INCORRECT_PIN_COUNT", nullable = false)
    private int incorrectPinCount;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

}
