package com.wefin.wefin.service;

import java.util.UUID;

public interface PaymentService {

    void makePayment(UUID loanId);
}
