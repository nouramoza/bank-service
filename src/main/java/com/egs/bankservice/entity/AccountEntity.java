//package com.egs.bankservice.entity;
//
//import com.sun.istack.NotNull;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "ACCOUNT")
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor
//@Getter
//@Setter
//public class AccountEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
////    @OneToMany(mappedBy = "CardEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
////    private List<CardEntity> cardList;
//
//    @Column(name = "ACCOUNT_NUMBER", nullable=false, unique = true)
//    @NonNull
//    private String accountNumber;
//
//    @Column(name = "PERSON_ID", nullable=false)
//    @NotNull
//    private Long personId;
//
//    @Column(name = "BALANCE", nullable=false)
//    @NotNull
//    private Long balance;
//
//    @Column(name = "IS_ACTIVE")
//    private Boolean isActive;
//}
