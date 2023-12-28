package com.wefin.wefin.service.impl;

import com.wefin.wefin.domain.enumeration.PaymentStatus;
import com.wefin.wefin.service.LoanService;
import com.wefin.wefin.service.PaymentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final LoanService loanService;

    public PaymentServiceImpl(
            @Lazy LoanService loanService
    ) {
        this.loanService = loanService;
    }

    @Override
    public void makePayment(UUID loanId) {
        loanService.updateStatus(loanId, PaymentStatus.PAID);
    }
}
