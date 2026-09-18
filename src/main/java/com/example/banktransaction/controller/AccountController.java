package com.example.banktransaction.controller;

import com.example.banktransaction.dto.CreateAccountRequest;
import com.example.banktransaction.dto.DepositRequest;
import com.example.banktransaction.entity.Account;
import com.example.banktransaction.entity.DepositHistory;
import com.example.banktransaction.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id,
                           @Valid @RequestBody DepositRequest request) {
        return accountService.deposit(id, request);
    }

    @GetMapping("/{id}/deposits")
    public List<DepositHistory> getDepositHistory(@PathVariable Long id) {
        return accountService.getDepositHistory(id);
    }
}
