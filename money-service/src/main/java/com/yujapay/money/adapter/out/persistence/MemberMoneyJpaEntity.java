package com.yujapay.money.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_money")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoneyJpaEntity {
    @Id
    @GeneratedValue
    private Long memberMoneyId;

    private Long membershipId;

    private int balance;

    private String aggregateIdIdentifier;

    public MemberMoneyJpaEntity(Long membershipId, int balance, String aggregateIdIdentifier) {
        this.membershipId = membershipId;
        this.balance = balance;
        this.aggregateIdIdentifier = aggregateIdIdentifier;
    }
}