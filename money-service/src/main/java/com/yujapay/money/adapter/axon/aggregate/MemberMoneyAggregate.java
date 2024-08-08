package com.yujapay.money.adapter.axon.aggregate;

import com.yujapay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.yujapay.money.adapter.axon.event.MemberMoneyCreatedEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
@Data
public class MemberMoneyAggregate {
    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;

    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
        System.out.println("MemberMoneyCreatedCommand Handler");

        apply(new MemberMoneyCreatedEvent(command.getMembershipId())); // 2. Event Store
    }

    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) { // 3. 이벤트 핸들러에서 aggregate 생성 후 Axon Fr 전달
        System.out.println("MemberMoneyCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance = 0;
    }

    public MemberMoneyAggregate() {
    }
}