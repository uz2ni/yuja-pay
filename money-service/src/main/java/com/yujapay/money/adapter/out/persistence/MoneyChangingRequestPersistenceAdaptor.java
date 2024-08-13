package com.yujapay.money.adapter.out.persistence;

import com.yujapay.common.PersistenceAdaptor;
import com.yujapay.money.application.port.in.CreateMemberMoneyPort;
import com.yujapay.money.application.port.out.IncreaseMoneyPort;
import com.yujapay.money.domain.MemberMoney;
import com.yujapay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@PersistenceAdaptor
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdaptor implements IncreaseMoneyPort, CreateMemberMoneyPort {

    private final SpringDataMoneyChangingRequestRepository moneyChangingRequestRepository;

    private final SpringDataMemberMoneyRepository memberMoneyRepository;

    @Override
    public MoneyChangingRequestJpaEntity createMoneyChangingRequest(MoneyChangingRequest.TargetMembershipId targetMembershipId, MoneyChangingRequest.MoneyChangingType moneyChangingType, MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount, MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus, MoneyChangingRequest.Uuid uuid) {
        return moneyChangingRequestRepository.save(
                new MoneyChangingRequestJpaEntity(
                        targetMembershipId.getTargetMembershipId(),
                        moneyChangingType.getChangingType(),
                        changingMoneyAmount.getMoneyAmount(),
                        new Timestamp(System.currentTimeMillis()), // TODO: YYYY-MM-DD hh:mm:ss
                        moneyChangingStatus.getChangingMoneyStatus(),
                        UUID.randomUUID()
                )
        );
    }

    @Override
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId membershipId, int increaseMoneyAmount) {
        MemberMoneyJpaEntity entity;
        try {
            List<MemberMoneyJpaEntity> entityList = memberMoneyRepository.findByMembershipId(Long.parseLong(membershipId.getMembershipId()));
            entity = entityList.get(0);

            entity.setBalance(entity.getBalance() + increaseMoneyAmount);
            return memberMoneyRepository.save(entity);
        } catch (Exception e) {
            entity = new MemberMoneyJpaEntity(
                    Long.parseLong(membershipId.getMembershipId()),
                    increaseMoneyAmount,
                    ""
            );
            entity = memberMoneyRepository.save(entity);
            return entity;
        }
    }

    @Override
    public void createMemberMoney(MemberMoney.MembershipId memberId, MemberMoney.MoneyAggregateIdentifier aggregateIdentifier) {
        MemberMoneyJpaEntity entity = new MemberMoneyJpaEntity(
                Long.parseLong(memberId.getMembershipId()),
                0,
                aggregateIdentifier.getAggregateIdentifier()
        );
        memberMoneyRepository.save(entity);
    }
}
