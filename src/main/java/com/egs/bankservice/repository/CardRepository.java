package com.egs.bankservice.repository;

import com.egs.bankservice.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    CardEntity findByCardNumber(String cardNumber);

}
