package com.yujapay.money.application.port.in;

import com.yujapay.common.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMoneyResultCommand extends SelfValidating<IncreaseMoneyResultCommand> {

    @NotNull
    private final String targetMembershipId;

    @NotNull
    private final int amount;


    public IncreaseMoneyResultCommand(String targetMembershipId, int amount) {
        this.targetMembershipId = targetMembershipId;
        this.amount = amount;

        this.validateSelf(); // constraints 어노테이션 붙은 필드 유효값 검사하도록 함. 이 부분 주석하면 @NotNull 같은거 붙어도 검사 안하고 통과함.
    }
}
