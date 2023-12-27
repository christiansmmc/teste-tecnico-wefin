package com.wefin.wefin.service.impl;

import com.wefin.wefin.domain.Person;
import com.wefin.wefin.domain.enumeration.IdentifierType;
import com.wefin.wefin.exception.EntityNotFoundException;
import com.wefin.wefin.exception.IdentifierTypeNotImplementedException;
import com.wefin.wefin.repository.PersonRepository;
import com.wefin.wefin.service.PersonService;
import com.wefin.wefin.util.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

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
            default -> throw new IdentifierTypeNotImplementedException();
        };
    }

    private BigDecimal getMinInstallmentValueByIdentifierType(IdentifierType identifierType) {
        return switch (identifierType) {
            case EU -> BigDecimal.valueOf(100);
            case PF -> BigDecimal.valueOf(300);
            case AP -> BigDecimal.valueOf(400);
            case PJ -> BigDecimal.valueOf(1000);
            default -> throw new IdentifierTypeNotImplementedException();
        };
    }

    private BigDecimal getMaxLoanValueByIdentifierType(IdentifierType identifierType) {
        return switch (identifierType) {
            case PF, EU -> BigDecimal.valueOf(10000);
            case AP -> BigDecimal.valueOf(25000);
            case PJ -> BigDecimal.valueOf(100000);
            default -> throw new IdentifierTypeNotImplementedException();
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
        Person personToUpdate = this.findById(person.getId());

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
