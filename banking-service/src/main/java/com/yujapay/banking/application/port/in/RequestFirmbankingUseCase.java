package com.yujapay.banking.application.port.in;


import com.yujapay.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingUseCase {

    FirmbankingRequest requestFirmbanking(FirmbankingRequestCommand command);

}