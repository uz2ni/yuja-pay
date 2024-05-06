package com.yujapay.banking.application.port.out;

import com.yujapay.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import com.yujapay.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingPort { // Adaptor 는 이 Port 인터페이스를 구현하는 구현체가 될 것임

    FirmBankingRequestJpaEntity createFirmbankingRequest(
            FirmbankingRequest.FromBankName fromBankName
            , FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber
            , FirmbankingRequest.ToBankName toBankName
            , FirmbankingRequest.ToBankAccountNumber toBankAccountNumber
            , FirmbankingRequest.MoneyAmount moneyAmount
            , FirmbankingRequest.FirmbankingStatus firmbankingStatus
    );

    FirmBankingRequestJpaEntity modifyFirmbankingRequest(
            FirmBankingRequestJpaEntity entity
    );
}
