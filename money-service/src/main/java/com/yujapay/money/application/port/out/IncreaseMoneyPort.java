package com.yujapay.money.application.port.out;

import com.yujapay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.yujapay.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.yujapay.money.domain.MemberMoney;
import com.yujapay.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyPort { // Adaptor 는 이 Port 인터페이스를 구현하는 구현체가 될 것임

    MoneyChangingRequestJpaEntity createMoneyChangingRequest(
            MoneyChangingRequest.TargetMembershipId targetMembershipId
            , MoneyChangingRequest.MoneyChangingType moneyChangingType
            , MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount
            , MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus
            , MoneyChangingRequest.Uuid uuid
    );

    MemberMoneyJpaEntity increaseMoney(
            MemberMoney.MembershipId membershipId,
            int increaseMoneyAmount
    );
}
