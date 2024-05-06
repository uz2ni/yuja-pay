package com.yujapay.banking.adapter.out.persistence;

import com.yujapay.banking.application.port.out.RequestFirmbankingPort;
import com.yujapay.banking.domain.FirmbankingRequest;
import com.yujapay.common.PersistenceAdaptor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@PersistenceAdaptor
@RequiredArgsConstructor
public class FirmbankingRequestPersistenceAdaptor implements RequestFirmbankingPort {

    private final SpringDataFirmbankingRequestRepository firmbankingRequestRepository;

    @Override
    public FirmBankingRequestJpaEntity createFirmbankingRequest(FirmbankingRequest.FromBankName fromBankName, FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber, FirmbankingRequest.ToBankName toBankName, FirmbankingRequest.ToBankAccountNumber toBankAccountNumber, FirmbankingRequest.MoneyAmount moneyAmount, FirmbankingRequest.FirmbankingStatus firmbankingStatus) {
        FirmBankingRequestJpaEntity entity = firmbankingRequestRepository.save(new FirmBankingRequestJpaEntity(
                fromBankName.getFromBankName(),
                fromBankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                moneyAmount.getMoneyAmount(),
                firmbankingStatus.getFirmbankingStatus(),
                UUID.randomUUID()
                )
        );
        return entity;
    }

    @Override
    public FirmBankingRequestJpaEntity modifyFirmbankingRequest(FirmBankingRequestJpaEntity entity) {
        return firmbankingRequestRepository.save(entity);
    }

}
