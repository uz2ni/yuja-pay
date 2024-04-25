package com.yujapay.banking.application.port.in;

import com.yujapay.common.SelfValidating;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false) // equals 메서드 썼을 때 슈퍼까지 포함 시켜서 해싱 할꺼냐, 해싱값으로 이퀄을 계산 할거냐
public class RegisterBankAccountCommand extends SelfValidating<RegisterBankAccountCommand> {

    @NotNull
    private final String membershipId;

    @NotNull
    private final String bankName;

    @NotNull
    @NotBlank // null 은 아니나 빈문자열 들어올 경우
    private final String bankAccountNumber;

    private final boolean linkedStatusIsValid;

    public RegisterBankAccountCommand(String membershipId, String bankName, String bankAccountNumber, boolean linkedStatusIsValid) {
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.linkedStatusIsValid = linkedStatusIsValid;

        this.validateSelf(); // constraints 어노테이션 붙은 필드 유효값 검사하도록 함. 이 부분 주석하면 @NotNull 같은거 붙어도 검사 안하고 통과함.
    }
}
