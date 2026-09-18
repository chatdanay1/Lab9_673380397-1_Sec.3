package com.example.banktransaction.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deposit_history")
public class DepositHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false, length = 20)
    private String accountNumber;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime depositedAt;

    public DepositHistory() {
    }

    public DepositHistory(Long accountId, String accountNumber, BigDecimal amount, LocalDateTime depositedAt) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.depositedAt = depositedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDepositedAt() {
        return depositedAt;
    }
}
