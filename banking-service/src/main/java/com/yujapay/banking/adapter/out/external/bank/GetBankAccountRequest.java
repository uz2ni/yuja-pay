package com.yujapay.banking.adapter.out.external.bank;

import lombok.Data;

@Data
public class GetBankAccountRequest {

    private String bankName;

    private String bankAccountName;

    public GetBankAccountRequest(String bankName, String bankAccountName) {
        this.bankName = bankName;
        this.bankAccountName = bankAccountName;
    }
}
