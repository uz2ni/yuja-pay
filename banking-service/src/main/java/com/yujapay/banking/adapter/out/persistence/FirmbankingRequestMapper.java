package com.yujapay.banking.adapter.out.persistence;

import com.yujapay.banking.domain.FirmbankingRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FirmbankingRequestMapper {

    public FirmbankingRequest mapToDomainEntity(FirmBankingRequestJpaEntity requestFirmBankingJpaEntity, UUID uuid) {
        return FirmbankingRequest.generateFirmbankingRequest(
                new FirmbankingRequest.FirmbankingRequestId(requestFirmBankingJpaEntity.getRequestFirmbankingId()+""),
                new FirmbankingRequest.FromBankName(requestFirmBankingJpaEntity.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(requestFirmBankingJpaEntity.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(requestFirmBankingJpaEntity.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(requestFirmBankingJpaEntity.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(requestFirmBankingJpaEntity.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(requestFirmBankingJpaEntity.getFirmbankingStatus()),
                uuid
        );
    }
}
