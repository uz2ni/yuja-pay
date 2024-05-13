package com.yujapay.money.adapter.out.service;

import lombok.Data;

@Data
public class Membership { // 실제 멤버십 서비스에서 사용하는 것과 내용은 동일하지만 동일한 클래스는 아님. 각 서비스에 필요한 것에 맞게 독립적으로 생성하여 사용.

    private String membershipId;

    private String name;

    private String email;

    private String address;

    private boolean isValid;

    private boolean isCorp;

}