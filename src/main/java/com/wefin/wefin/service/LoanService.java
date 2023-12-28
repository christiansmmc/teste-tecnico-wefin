package com.wefin.wefin.service;

import com.wefin.wefin.domain.Loan;
import com.wefin.wefin.domain.enumeration.PaymentStatus;

import java.util.UUID;

public interface LoanService {

    Loan create(Loan loan);

    Loan findById(UUID id);

    Loan save(Loan loan);

    Loan updateStatus(UUID id, PaymentStatus status);
}
