package com.ingenico.assessment.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingenico.assessment.accountmanagement.model.Account;

@Repository("accountRepository")
public interface AccountRepository extends JpaRepository<Account, Long>{

}
