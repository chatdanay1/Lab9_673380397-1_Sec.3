package com.example.banktransaction.service;

import com.example.banktransaction.dto.CreateAccountRequest;
import com.example.banktransaction.dto.DepositRequest;
import com.example.banktransaction.entity.Account;
import com.example.banktransaction.entity.DepositHistory;
import com.example.banktransaction.repository.AccountRepository;
import com.example.banktransaction.repository.DepositHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final DepositHistoryRepository depositHistoryRepository;

    public AccountService(AccountRepository accountRepository,
                          DepositHistoryRepository depositHistoryRepository) {
        this.accountRepository = accountRepository;
        this.depositHistoryRepository = depositHistoryRepository;
    }

    public Account createAccount(CreateAccountRequest request) {
        if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new IllegalArgumentException("Account number already exists");
        }

        Account account = new Account(
                request.getAccountNumber(),
                request.getAccountName(),
                request.getInitialBalance()
        );

        return accountRepository.save(account);
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<DepositHistory> getDepositHistory(Long accountId) {
        getAccount(accountId);
        return depositHistoryRepository.findByAccountIdOrderByDepositedAtDesc(accountId);
    }

    @Transactional
    public Account deposit(Long accountId, DepositRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        if (request.isSimulateFailure()) {
            throw new RuntimeException("Simulated error: transaction must rollback");
        }

        DepositHistory history = new DepositHistory(
                account.getId(),
                account.getAccountNumber(),
                request.getAmount(),
                LocalDateTime.now()
        );

        depositHistoryRepository.save(history);

        return account;
    }
}
