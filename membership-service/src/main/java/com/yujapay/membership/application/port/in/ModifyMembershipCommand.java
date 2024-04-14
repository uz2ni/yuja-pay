package com.yujapay.membership.application.port.in;

import com.yujapay.common.SelfValidating;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ModifyMembershipCommand extends SelfValidating<ModifyMembershipCommand> {

    @NotNull
    private final String membershipId;

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

    public ModifyMembershipCommand(String membershipId, String name, String email, String address, boolean isValid, boolean isCorp) {
        this.membershipId = membershipId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;

        this.validateSelf(); // constraints 어노테이션 붙은 필드 유효값 검사하도록 함. 이 부분 주석하면 @NotNull 같은거 붙어도 검사 안하고 통과함.
    }
}
