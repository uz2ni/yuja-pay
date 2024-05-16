package com.yujapay.money.application.port.out;

import com.yujapay.common.RechargingMoneyTask;

public interface SendRechargingMoneyTaskPort { // kafka 클러스터를 async 프로듀스 하기 위한 포트
    void sendRechargingMoneyTaskPort(
            RechargingMoneyTask task
    );
}