package com.yujapay.money.application.port.in;

import com.yujapay.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyResultUseCase {

    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyResultCommand command);

}