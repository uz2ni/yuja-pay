package com.yujapay.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RechargingMoneyTask { // Increase Money

    private String taskID;

    private String taskName;

    private String membershipID;

    private List<SubTask> subTaskList;

    // 법인 계좌 (유자페이 계좌로 돈을 얼마나 보내서 충전할지 정보)
    private String toBankName;

    // 법인 계좌 번호
    private String toBankAccountNumber;

    private int moneyAmount; // only won
}