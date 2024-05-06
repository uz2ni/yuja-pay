package com.yujapay.banking.adapter.out.external.bank;

import lombok.Data;

@Data
public class BankAccount {

    private String bankName;

    private String bankAccountName;

    private boolean isValid;

    public BankAccount(String bankName, String bankAccountName, boolean isValid) {
        this.bankName = bankName;
        this.bankAccountName = bankAccountName;
        this.isValid = isValid;
    }
}
