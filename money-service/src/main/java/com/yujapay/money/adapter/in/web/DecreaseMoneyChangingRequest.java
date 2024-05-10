package com.yujapay.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecreaseMoneyChangingRequest { // 무조건 증액 요청

    private String targetMembershipId;

    private String amount;

}