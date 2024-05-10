package com.yujapay.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyChangingRequest {

    @Getter private final String moneyChangingRequestId;

    // 어떤 고객이 증/감 요청 했는지 고객 정보
    @Getter private final String targetMembershipId;

    // 그 요청이 증액/감액 요청 인지
    @Getter private final int changingType; // enum, 0: 증액 1: 감액

    // 요청 금액
    @Getter private final int changingMoneyAmount;

    // 머니 변액 요청에 대한 상태
    @Getter private final int changingMoneyStatus; // enum

    @Getter private final String uuid;

    @Getter private final Date createdAt;

    public static MoneyChangingRequest generateMoneyChangingRequest(
            MoneyChangingRequestId moneyChangingRequestId,
            TargetMembershipId targetMembershipId,
            MoneyChangingType moneyChangingType,
            ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingMoneyStatus moneyChangingMoneyStatus,
            String uuid
            ) {
        return new MoneyChangingRequest(
                moneyChangingRequestId.moneyChangingRequestId,
                targetMembershipId.targetMembershipId,
                moneyChangingType.changingType,
                changingMoneyAmount.moneyAmount,
                moneyChangingMoneyStatus.changingMoneyStatus,
                uuid,
                new Date()
        );
    }

    // 본 클래스에 직접 접근하지 않고, 싱글톤 패턴처럼 아래 public 클래스 따로줘서 객체 생성할 수 있도록 함
    @Value
    public static class MoneyChangingRequestId {
        public MoneyChangingRequestId(String value) {
            this.moneyChangingRequestId = value;
        }
        String moneyChangingRequestId ;
    }

    @Value
    public static class TargetMembershipId {
        public TargetMembershipId(String value) {
            this.targetMembershipId = value;
        }
        String targetMembershipId ;
    }

    @Value
    public static class MoneyChangingType {
        public MoneyChangingType(int value) {
            this.changingType = value;
        }
        int changingType ;
    }

    @Value
    public static class ChangingMoneyAmount {
        public ChangingMoneyAmount(int value) {
            this.moneyAmount = value;
        }
        int moneyAmount ;
    }

    @Value
    public static class MoneyChangingMoneyStatus {
        public MoneyChangingMoneyStatus(int value) {
            this.changingMoneyStatus = value;
        }
        int changingMoneyStatus ;
    }

    @Value
    public static class Uuid {
        public Uuid(String uuid) {
            this.uuid = uuid;
        }
        String uuid ;
    }

}
