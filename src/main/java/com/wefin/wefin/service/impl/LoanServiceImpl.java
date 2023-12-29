package com.wefin.wefin.service.impl;

import com.wefin.wefin.domain.Loan;
import com.wefin.wefin.domain.Person;
import com.wefin.wefin.domain.enumeration.IdentifierType;
import com.wefin.wefin.domain.enumeration.PaymentStatus;
import com.wefin.wefin.exception.EntityNotFoundException;
import com.wefin.wefin.exception.InvalidIdentifierException;
import com.wefin.wefin.exception.InvalidInstallmentNumberException;
import com.wefin.wefin.exception.InvalidLoanValueException;
import com.wefin.wefin.repository.LoanRepository;
import com.wefin.wefin.service.LoanService;
import com.wefin.wefin.service.PaymentService;
import com.wefin.wefin.service.PersonService;
import com.wefin.wefin.util.IdentifierValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;
    private final PersonService personService;
    private final PaymentService paymentService;

    private static final int MAX_ALLOWED_INSTALLMENTS = 24;

    @Autowired
    public LoanServiceImpl(
            LoanRepository repository,
            PersonService personService,
            PaymentService paymentService
    ) {
        this.repository = repository;
        this.personService = personService;
        this.paymentService = paymentService;
    }

    @Override
    public Loan create(Loan loan) {
        Person person = personService.findById(loan.getPerson().getId());

        this.throwIfIdentifierIsInvalid(
                person.getIdentifierType(),
                person.getIdentifier()
        );
        this.throwIfLoanValuesAreInvalid(
                loan.getLoanValue(),
                person.getMaxLoanValue(),
                person.getMinInstallmentValue(),
                loan.getInstallmentsTotal()
        );

        loan.setPaymentStatus(PaymentStatus.PENDING);
        loan.setPerson(person);
        loan.setCreatedDate(LocalDateTime.now());
        Loan loanCreated = repository.saveAndFlush(loan);

        paymentService.makePayment(loanCreated.getId());

        return loanCreated;
    }

    private void throwIfIdentifierIsInvalid(
            IdentifierType identifierType,
            String identifier
    ) {
        switch (identifierType) {
            case PF -> {
                if (!IdentifierValidator.isCpf(identifier))
                    throw new InvalidIdentifierException("Invalid CPF number");
            }
            case PJ -> {
                if (!IdentifierValidator.isCnpj(identifier))
                    throw new InvalidIdentifierException("Invalid CNPJ number");
            }
            case EU -> {
                if (!IdentifierValidator.isStudent(identifier))
                    throw new InvalidIdentifierException("Invalid Student number");
            }
            case AP -> {
                if (!IdentifierValidator.isRetiree(identifier))
                    throw new InvalidIdentifierException("Invalid Retiree number");
            }
        }
    }

    private void throwIfLoanValuesAreInvalid(
            BigDecimal loanValue,
            BigDecimal personMaxLoanValue,
            BigDecimal personMinInstallmentValue,
            Long loanInstallmentsTotal
    ) {
        BigDecimal installmentValue = loanValue.divide(BigDecimal.valueOf(loanInstallmentsTotal), RoundingMode.HALF_EVEN);

        if (loanValue.compareTo(personMaxLoanValue) > 0)
            throw new InvalidLoanValueException("Loan value exceeds maximum allowed value for the person.");
        if (loanInstallmentsTotal > MAX_ALLOWED_INSTALLMENTS)
            throw new InvalidInstallmentNumberException("Installment number is higher than 24 installments");
        if (personMinInstallmentValue.compareTo(installmentValue) > 0)
            throw new InvalidInstallmentNumberException("Installment value is lower than minimum allowed value for the person person");
    }

    @Override
    public Loan findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Loan.class));
    }

    @Override
    public Loan save(Loan loan) {
        return repository.save(loan);
    }

    @Override
    public Loan updateStatus(UUID id, PaymentStatus status) {
        Loan loan = this.findById(id);
        loan.setPaymentStatus(PaymentStatus.PAID);
        return this.save(loan);
    }
}
