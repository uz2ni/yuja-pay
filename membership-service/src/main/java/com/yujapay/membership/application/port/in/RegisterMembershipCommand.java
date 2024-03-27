package com.yujapay.membership.application.port.in;

import common.SelfValidating;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false) // equals 메서드 썼을 때 슈퍼까지 포함 시켜서 해싱 할꺼냐, 해싱값으로 이퀄을 계산 할거냐
public class RegisterMembershipCommand extends SelfValidating<RegisterMembershipCommand> {

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @NotNull
    @NotBlank // null 은 아니나 빈문자열 들어올 경우
    private final String address;

    @AssertTrue // 항상 true
    private final boolean isValid;

    private final boolean isCorp;

    public RegisterMembershipCommand(String name, String email, String address, boolean isValid, boolean isCorp) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;

        this.validateSelf(); // constraints 어노테이션 붙은 필드 유효값 검사하도록 함. 이 부분 주석하면 @NotNull 같은거 붙어도 검사 안하고 통과함.
    }
}
