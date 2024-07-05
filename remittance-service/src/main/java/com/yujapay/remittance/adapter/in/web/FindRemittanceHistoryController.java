package com.yujapay.remittance.adapter.in.web;

import com.yujapay.common.WebAdapter;
import com.yujapay.remittance.application.port.in.FindRemittanceCommand;
import com.yujapay.remittance.application.port.in.FindRemittanceUseCase;
import com.yujapay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindRemittanceHistoryController {

    private final FindRemittanceUseCase findRemittanceUseCase;
    @GetMapping( "/remittance/{membershipId}")
    List<RemittanceRequest> findRemittanceHistory(@PathVariable String membershipId) {
        FindRemittanceCommand command = FindRemittanceCommand.builder()
                .membershipId(membershipId)
                .build();

        return findRemittanceUseCase.findRemittanceHistory(command);
    }
}