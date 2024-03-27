package com.yujapay.membership.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMembershipRequest {
    // membershipId, isValid 는 시스템에서 관리하며 자동 지정하거나 로직에서 정의하는 데이터라서 없음

    private String name;

    private String email;

    private String address;

    private boolean isCorp;
}
