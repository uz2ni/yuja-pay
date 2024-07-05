package com.yujapay.remittance.application.service;

import com.yujapay.common.UseCase;
import com.yujapay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.yujapay.remittance.application.port.in.FindRemittanceCommand;
import com.yujapay.remittance.application.port.in.FindRemittanceUseCase;
import com.yujapay.remittance.application.port.out.FindRemittancePort;
import com.yujapay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {
    private final FindRemittancePort findRemittancePort;
    private final RemittanceRequestMapper mapper;

    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        //
        findRemittancePort.findRemittanceHistory(command);
        return null;
    }
}