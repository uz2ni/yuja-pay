package com.yujapay.money.application.service;

import com.yujapay.common.CountDownLatchManager;
import com.yujapay.common.RechargingMoneyTask;
import com.yujapay.common.SubTask;
import com.yujapay.common.UseCase;
import com.yujapay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.yujapay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.yujapay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.yujapay.money.application.port.in.*;
import com.yujapay.money.application.port.out.GetMembershipPort;
import com.yujapay.money.application.port.out.IncreaseMoneyPort;
import com.yujapay.money.application.port.out.MembershipStatus;
import com.yujapay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.yujapay.money.domain.MemberMoney;
import com.yujapay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyResultUseCase, CreateMemberMoneyUseCase {

    private final CountDownLatchManager countDownLatchManager;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final GetMembershipPort getMembershipPort;
    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper mapper;
    private final CommandGateway commandGateway;
    private final CreateMemberMoneyPort createMemberMoneyPort;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyResultCommand command) {

        // 1. 고객 정보가 정상인지 확인 (멤버)
        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getTargetMembershipId());

        // 2. 고객의 연동 계좌 있는지, 잔액 충분한지 확인 (뱅킹)

        // 3. 법인 계좌 상태 정상인지 확인 (뱅킹)

        // 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingRequest 생성 (MoneyChangingRequest)

        // 5. 펌뱅킹 수행 (고객의 연동 계좌 -> 유자페이 법인 계좌) (뱅킹)

        // 6-1. 결과 정상이면, 성공으로 상태값 변동 후 리턴
        // 성공 시 memberMoney 값 증액 필요
        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),
                command.getAmount()
        );

        if(memberMoneyJpaEntity != null) {
            return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.MoneyChangingType(1),
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.MoneyChangingStatus(1),
                    new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
            ));
        }

        // 6-2. 결과 실패이면, 실패로 상태값 변동 후 리턴
        return null;


    }

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyResultCommand command) {

        // Subtask
        // 각 서비스에 특정 membershipId 로 Validation 을 하기위한 Task.

        // 1. Subtask, Task
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask : " + "멤버십 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // Banking Sub task
        // Banking Account Validation
        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingAccountTask : " + "뱅킹 계좌 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();

        // Amount Money Firmbanking --> 무조건 ok 받았다고 가정.

        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("Increase Money Task / 머니 충전 Task")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipID(command.getTargetMembershipId())
                .toBankName("yujabank")
                .build();

        // 2. Kafka Cluster Produce
        // Task Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
        countDownLatchManager.addCountDownLatch(task.getTaskID());

        // 3. Wait
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskID()).await(); // task-consumer 에서 해당 키를 가진 레치 받아와서 카운트다운하면,await()에서 멈춰있다가 3-1 진행
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 3-1. task-consumer
        //  등록된 sub-task, status 모두 ok -> task 결과를 Produce

        // 4. Task Result Consume
        // 받은 응답을 다시, countDownLatchManager 를 통해서 결과 데이터를 받아야 해요.
        String result = countDownLatchManager.getDataForKey(task.getTaskID());
        if (result.equals("success")) {
            // 4-1. Consume ok, Logic
            MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId())
                    , command.getAmount());

            if (memberMoneyJpaEntity != null) {
                return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                                new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                                new MoneyChangingRequest.MoneyChangingType(1),
                                new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                                new MoneyChangingRequest.MoneyChangingStatus(1),
                                new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                        )
                );
            }
        } else {
            // 4-2. Consume fail, Logic
            return null;
        }
        // 5. Consume ok, Logic
        return null;

    }


//
//    @Override
//    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {
//
//        // 은행 계좌를 등록해야하는 서비스 (비즈니스 로직)
//        // command.getMembershipId() // 이 멤버십 계좌가 유효하니? 라고 membership 서비스에 물어봐야 함. 일단 생략.(유효하다고 가정)
//
//        // (멤버 서비스도 확인?) 여기서는 skip
//
//        // 1. 외부 실제 은행에 등록이 가능한 계좌인지(정상인지) 확인한다.
//        // 외부의 은행에 이 계좌 정상인지? 확인을 해야해요.
//        // Biz Logic -> External System
//        // Port -> Adapter -> External System
//        // Port
//
//        // 실제 외부의 은행계좌 정보를 Get
//        BankAccount bankAccountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
//        boolean accountIsValid = bankAccountInfo.isValid();
//
//        // 2. 등록 가능한 계좌라면 등록한다. 성공하면 등록 정보 리턴
//        // 2-1. 등록 가능하지 않은 계좌라면 에러 리턴
//        if(accountIsValid) {
//            // 등록 정보 저장
//            RegisteredBankAccountJpaEntity saveAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
//                    new RegisteredBankAccount.MembershipId(command.getMembershipId()+""),
//                    new RegisteredBankAccount.BankName(command.getBankName()),
//                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
//                    new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid())
//            );
//            return mapper.mapToDomainEntity(saveAccountInfo);
//        }else {
//            return null;
//        }
//    }

    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {
        MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getMembershipId());
        // 1. axon fr -> axon server(event queue) 쓰는 구간
        commandGateway.send(axonCommand).whenComplete((result, throwable) -> { // whenComplete : 요청 시키고 기다림
            if (throwable != null) {
                System.out.println("throwable = " + throwable);
                throw new RuntimeException(throwable);
            } else{
                System.out.println("result = " + result);
                createMemberMoneyPort.createMemberMoney(
                        new MemberMoney.MembershipId(command.getMembershipId()),
                        new MemberMoney.MoneyAggregateIdentifier(result.toString())
                );
            }
        });
    }
}
