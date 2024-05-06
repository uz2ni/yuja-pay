package com.yujapay.banking.adapter.out.external.bank;

import com.yujapay.banking.application.port.out.RequestBankAccountInfoPort;
import com.yujapay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.yujapay.banking.domain.FirmbankingRequest;
import com.yujapay.common.ExternalSystemAdaptor;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdaptor
@RequiredArgsConstructor
public class BankAccountAdaptor implements RequestBankAccountInfoPort, RequestExternalFirmbankingPort {

    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {

        // 실제 외부 은행에 http 을 통해서 계좌 정보 가져오고
        // 실제 계좌 정보 -> BankAccount 파싱 했다고 가정
        return new BankAccount(request.getBankName(), request.getBankAccountName(), true);
    }

    @Override
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request) {

        // 실제 외부 은행에 http 통신 통해
        // 펌뱅킹 요청을 하고

        // 그 결과를
        // 외부 은행의 실제 결과를 -> 유자 페이의 FirmbankingResult 파싱\
        return new FirmbankingResult(0);
    }
}
