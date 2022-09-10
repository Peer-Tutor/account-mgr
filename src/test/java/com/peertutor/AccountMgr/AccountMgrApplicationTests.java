package com.peertutor.AccountMgr;

import com.peertutor.AccountMgr.model.Account;
import com.peertutor.AccountMgr.model.enumeration.UserType;
import com.peertutor.AccountMgr.repository.AccountRepository;
import com.peertutor.AccountMgr.service.dto.AccountDTO;
import com.peertutor.AccountMgr.service.mapper.AccountMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountMgrApplicationTests {

    private static final String USER_NAME_1 = "user_1";
    private static final String USER_PASSWORD_1 = "aaaaaa";
    private static final String USER_SESSION_TOKEN_1 = "aaaaaa";
    private static final String USER_USERTYPE_1 = "TUTOR";
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;

    @AfterAll
    public void cleanUp() {
        List<Account> accountSaved = accountRepository.findByNameIn(new ArrayList<String>(Arrays.asList(USER_NAME_1)));
        accountSaved.stream().forEach((account) -> accountRepository.delete(account));
        accountRepository.flush();
    }

    @Test
    @Order(1)
    public void saveAccountSuccess() {

        AccountDTO accountDTO = AccountDTO.builder()
                .name(USER_NAME_1)
                .password(USER_PASSWORD_1)
                .sessionToken(USER_SESSION_TOKEN_1)
                .userType(UserType.valueOf(USER_USERTYPE_1))
                .build();
        Account account = accountMapper.toEntity(accountDTO);
        Account accountSaved = accountRepository.saveAndFlush(account);

        Assertions.assertThat(account.equals(accountSaved));
    }

    @Test
    @Order(2)
    public void saveDuplicateAccountFail() {
        AccountDTO accountDTO = AccountDTO.builder()
                .name(USER_NAME_1)
                .password(USER_PASSWORD_1)
                .sessionToken(USER_SESSION_TOKEN_1)
                .userType(UserType.valueOf(USER_USERTYPE_1))
                .build();
        Account account = accountMapper.toEntity(accountDTO);
        Exception thrown = assertThrows(DataIntegrityViolationException.class, () -> {
            accountRepository.saveAndFlush(accountMapper.toEntity(accountDTO));
        });
    }
}
