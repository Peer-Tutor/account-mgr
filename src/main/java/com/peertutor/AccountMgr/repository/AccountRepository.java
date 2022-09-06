package com.peertutor.AccountMgr.repository;

import com.peertutor.AccountMgr.model.Account;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByLastName(String lastName);
    Account findById(long id);
}

