package com.yujapay.banking.application.port.out;

public interface GetMembershipPort { // Adaptor 는 이 Port 인터페이스를 구현하는 구현체가 될 것임

    MembershipStatus getMembership(String membershipId);

}
