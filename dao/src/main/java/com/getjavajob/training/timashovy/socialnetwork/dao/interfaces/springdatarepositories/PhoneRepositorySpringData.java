package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories;

import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneRepositorySpringData extends CrudRepository<Phone, Long> {

    @Query("select p.number from Phone p where p.account.id = :accountId and p.phoneType = :phoneType")
    List<String> findPhoneNumbersByAccountIdAndPhoneType(@Param("accountId") Long accountId,
                                                         @Param("phoneType") PhoneType phoneType);

}
