package com.yujapay.banking.application.port.out;

import com.yujapay.banking.adapter.out.external.bank.BankAccount;
import com.yujapay.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {

    BankAccount getBankAccountInfo(GetBankAccountRequest request);

}
