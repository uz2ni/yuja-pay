package com.yujapay.banking.adapter.out.external.bank;

import com.yujapay.banking.application.port.out.RequestBankAccountInfoPort;
import com.yujapay.common.ExternalSystemAdaptor;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdaptor
@RequiredArgsConstructor
public class BankAccountAdaptor implements RequestBankAccountInfoPort {

    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {

        // 실제 외부 은행에 http 을 통해서 계좌 정보 가져오고
        // 실제 계좌 정보 -> BankAccount 파싱 했다고 가정
        return new BankAccount(request.getBankName(), request.getBankAccountName(), true);
    }
}
