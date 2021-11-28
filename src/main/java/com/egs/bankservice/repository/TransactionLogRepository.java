package com.egs.bankservice.repository;

import com.egs.bankservice.entity.TransactionLogEntity;
import com.egs.bankservice.enums.RequestTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLogEntity, Long> {

    @Query("select tl from TransactionLogEntity tl where tl.accountEntity.accountNumber =:accountNumber and tl.requestDate <= :toDate and tl.requestDate >= :fromDate and tl.requestTypeEnum in (:requestTyp) and tl.status = 0 order by tl.requestDate desc")
    public List<TransactionLogEntity> getReceipt(@Param("accountNumber") String accountNumber,
                                                    @Param("fromDate") Date fromDate,
                                                    @Param("toDate") Date toDate,
                                                    @Param("requestTyp") List<RequestTypeEnum> requestTyp
                                                 );
}
