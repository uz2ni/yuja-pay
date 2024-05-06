package com.yujapay.banking.application.port.out;

import com.yujapay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.yujapay.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountPort { // Adaptor 는 이 Port 인터페이스를 구현하는 구현체가 될 것임

    RegisteredBankAccountJpaEntity createRegisteredBankAccount(
            RegisteredBankAccount.MembershipId membershipId
            , RegisteredBankAccount.BankName bankName
            , RegisteredBankAccount.BankAccountNumber bankAccountNumber
            , RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid
    );
}
