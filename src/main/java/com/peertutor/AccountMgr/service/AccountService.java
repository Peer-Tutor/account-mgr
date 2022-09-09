package com.peertutor.AccountMgr.service;

import com.peertutor.AccountMgr.model.Account;
import com.peertutor.AccountMgr.repository.AccountRepository;
import com.peertutor.AccountMgr.service.dto.AccountDTO;
import com.peertutor.AccountMgr.service.mapper.AccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public Optional<Account> getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account;
    }

    public AccountDTO saveAccount(AccountDTO accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    public AccountDTO updateAccount(AccountDTO accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }
}
