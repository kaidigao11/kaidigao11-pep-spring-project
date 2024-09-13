package com.example.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Transactional
@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    
    public Account containsAccount(String userName){
        if (accountRepository.findAccountByUsername(userName)!=null) {
            return accountRepository.findAccountByUsername(userName);
        }
        return null;
    }

    public Account createNewAccount(Account account){
        return accountRepository.save(account);
    }

    public Account loginAccount(Account account){
        return accountRepository.findAccountByUsernameAndPassword(account.getUsername(),account.getPassword());

    }

}
