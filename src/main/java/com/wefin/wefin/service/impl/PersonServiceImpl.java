package com.wefin.wefin.service.impl;

import com.wefin.wefin.domain.Person;
import com.wefin.wefin.domain.enumeration.IdentifierType;
import com.wefin.wefin.exception.EntityNotFoundException;
import com.wefin.wefin.exception.IdentifierTypeNotImplementedException;
import com.wefin.wefin.repository.PersonRepository;
import com.wefin.wefin.service.PersonService;
import com.wefin.wefin.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    private static final BigDecimal EU_MIN_INSTALLMENT_VALUE = BigDecimal.valueOf(100);
    private static final BigDecimal PF_MIN_INSTALLMENT_VALUE = BigDecimal.valueOf(300);
    private static final BigDecimal AP_MIN_INSTALLMENT_VALUE = BigDecimal.valueOf(400);
    private static final BigDecimal PJ_MIN_INSTALLMENT_VALUE = BigDecimal.valueOf(1000);
    private static final BigDecimal EU_MAX_LOAN_VALUE = BigDecimal.valueOf(10000);
    private static final BigDecimal PF_MAX_LOAN_VALUE = BigDecimal.valueOf(10000);
    private static final BigDecimal AP_MAX_LOAN_VALUE = BigDecimal.valueOf(25000);
    private static final BigDecimal PJ_MAX_LOAN_VALUE = BigDecimal.valueOf(100000);

    @Autowired
    public PersonServiceImpl(
            PersonRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Person create(Person person) {
        String personIdentifier = StringUtils.removeAllExceptNumber(person.getIdentifier());

        IdentifierType identifierType = this.getIdentifierTypeByIdentifier(personIdentifier);
        BigDecimal minInstallmentValue = this.getMinInstallmentValueByIdentifierType(identifierType);
        BigDecimal maxLoanValue = this.getMaxLoanValueByIdentifierType(identifierType);

        person.setIdentifierType(identifierType);
        person.setMinInstallmentValue(minInstallmentValue);
        person.setMaxLoanValue(maxLoanValue);
        person.setIdentifier(personIdentifier);

        return repository.save(person);
    }

    private IdentifierType getIdentifierTypeByIdentifier(String identifier) {
        String personIdentifier = StringUtils.removeAllExceptNumber(identifier);

        return switch (personIdentifier.length()) {
            case 8 -> IdentifierType.EU;
            case 10 -> IdentifierType.AP;
            case 11 -> IdentifierType.PF;
            case 14 -> IdentifierType.PJ;
            default ->
                    throw new IdentifierTypeNotImplementedException("The provided identifier has no identifier type");
        };
    }

    private BigDecimal getMinInstallmentValueByIdentifierType(IdentifierType identifierType) {
        return switch (identifierType) {
            case EU -> EU_MIN_INSTALLMENT_VALUE;
            case PF -> PF_MIN_INSTALLMENT_VALUE;
            case AP -> AP_MIN_INSTALLMENT_VALUE;
            case PJ -> PJ_MIN_INSTALLMENT_VALUE;
            default ->
                    throw new IdentifierTypeNotImplementedException("The provided identifier type has no min installment value");
        };
    }

    private BigDecimal getMaxLoanValueByIdentifierType(IdentifierType identifierType) {
        return switch (identifierType) {
            case EU -> EU_MAX_LOAN_VALUE;
            case PF -> PF_MAX_LOAN_VALUE;
            case AP -> AP_MAX_LOAN_VALUE;
            case PJ -> PJ_MAX_LOAN_VALUE;
            default ->
                    throw new IdentifierTypeNotImplementedException("The provided identifier type has no max loan value");
        };
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(UUID id) {
        Person person = this.findById(id);
        repository.delete(person);
    }

    @Override
    public Person update(UUID id, Person person) {
        Person personToUpdate = this.findById(id);

        String personIdentifier = StringUtils.removeAllExceptNumber(person.getIdentifier());
        IdentifierType identifierType = this.getIdentifierTypeByIdentifier(personIdentifier);
        BigDecimal minInstallmentValue = this.getMinInstallmentValueByIdentifierType(identifierType);
        BigDecimal maxLoanValue = this.getMaxLoanValueByIdentifierType(identifierType);

        personToUpdate.setName(person.getName());
        personToUpdate.setBirthDate(person.getBirthDate());
        personToUpdate.setIdentifierType(identifierType);
        personToUpdate.setMinInstallmentValue(minInstallmentValue);
        personToUpdate.setMaxLoanValue(maxLoanValue);
        personToUpdate.setIdentifier(personIdentifier);

        return repository.save(personToUpdate);
    }

    @Override
    public Person findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Person.class));
    }
}
