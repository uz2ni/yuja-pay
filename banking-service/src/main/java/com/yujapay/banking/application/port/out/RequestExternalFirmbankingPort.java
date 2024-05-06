package com.yujapay.banking.application.port.out;

import com.yujapay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.yujapay.banking.adapter.out.external.bank.FirmbankingResult;

public interface RequestExternalFirmbankingPort {

    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);

}
