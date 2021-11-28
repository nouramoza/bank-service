//package com.egs.bankservice.repository;
//
//import com.egs.bankservice.entity.CardEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CardRepository extends JpaRepository<CardEntity, Long> {
//
////    @Query("select c from CardEntity c where c.cardNumber =:cardNumber")
////    public List<CardEntity> searchForCard(@Param("cardNumber") String cardNumber);
//
//    public CardEntity findByCardNumber(String cardNumber);
//
//    public CardEntity findByCardNumberAndPin(String cardNumber, String pin);
//
//}
