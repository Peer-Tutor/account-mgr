package com.peertutor.AccountMgr.service;

import com.peertutor.AccountMgr.model.Account;
import com.peertutor.AccountMgr.repository.AccountRepository;
import com.peertutor.AccountMgr.security.JWTUtils;
import com.peertutor.AccountMgr.service.dto.AccountDTO;
import com.peertutor.AccountMgr.service.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountMapper accountMapper;
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public AccountDTO registerNewUserAccount(AccountDTO accountDTO) {
        Account account = accountRepository.findByName(accountDTO.getName());
        if (account != null) {
            return null;
        } else {
            account = new Account();
        }

        account.setName(accountDTO.getName());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setUserType(accountDTO.getUserType());

        try {
            account = accountRepository.save(account);
        } catch (Exception e) {
            logger.error("Registration Failed: " + e.getMessage());
            return null;
        }

        AccountDTO result = accountMapper.toDto(account);
        result.setSessionToken(JWTUtils.generateJwtToken(result));
        result.setPassword("");

        return result;
    }

    public AccountDTO loginExistingUserAccount(AccountDTO accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        Account existingAccount = accountRepository.findByName(account.getName());

        AccountDTO result = null;

        if (BCrypt.checkpw(accountDTO.getPassword(), existingAccount.getPassword())
                && account.getUserType().equals(existingAccount.getUserType())) {
            result = accountMapper.toDto(account);
            result.setSessionToken(JWTUtils.generateJwtToken(result));
        }

        return result;
    }

    public Boolean hasValidTokenAuthentication(String name, String sessionToken) {
        return JWTUtils.validateJwtToken(name, sessionToken);
    }

    public AccountDTO updateAccount(AccountDTO accountDTO) {
        Account account = accountRepository.findByName(accountDTO.getName());
        if (account == null) {
            return null;
        }

        account.setName(accountDTO.getName());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setUserType(accountDTO.getUserType());

        try {
            account = accountRepository.save(account);
        } catch (Exception e) {
            logger.error("Registration Failed: " + e.getMessage());
            return null;
        }

        AccountDTO result = accountMapper.toDto(account);
        result.setSessionToken(JWTUtils.generateJwtToken(result));
        result.setPassword("");

        return result;
    }
}
