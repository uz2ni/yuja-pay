package com.yujapay.remittance.application.port.out;

import com.yujapay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.yujapay.remittance.application.port.in.FindRemittanceCommand;

import java.util.List;

public interface FindRemittancePort {

    List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);
}