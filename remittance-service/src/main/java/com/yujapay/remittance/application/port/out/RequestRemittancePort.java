package com.yujapay.remittance.application.port.out;

import com.yujapay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.yujapay.remittance.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {

    RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command);
    boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity);
}