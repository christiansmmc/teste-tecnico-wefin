package com.wefin.wefin.service;

import com.wefin.wefin.domain.Person;
import com.wefin.wefin.domain.enumeration.IdentifierType;
import com.wefin.wefin.exception.IdentifierTypeNotImplementedException;
import com.wefin.wefin.repository.PersonRepository;
import com.wefin.wefin.service.impl.PersonServiceImpl;
import com.wefin.wefin.util.StringUtils;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    private PersonService service;

    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        service = new PersonServiceImpl(repository);
    }

    @Test
    void create_withPFIdentifier() {
        Person person = Person
                .builder()
                .name(faker.name().fullName())
                .identifier(faker.cpf().valid())
                .birthDate(LocalDate.of(1998, 6, 6))
                .build();

        Person expectedPerson = Person
                .builder()
                .name(person.getName())
                .identifier(StringUtils.removeAllExceptNumber(person.getIdentifier()))
                .birthDate(person.getBirthDate())
                .minInstallmentValue(BigDecimal.valueOf(300))
                .maxLoanValue(BigDecimal.valueOf(10000))
                .identifierType(IdentifierType.PF)
                .build();

        Mockito
                .when(repository.save(expectedPerson))
                .thenReturn(expectedPerson);

        service.create(person);

        Mockito
                .verify(repository)
                .save(expectedPerson);
    }

    @Test
    void create_withPJIdentifier() {
        Person person = Person
                .builder()
                .name(faker.name().fullName())
                .identifier(faker.cnpj().valid())
                .birthDate(LocalDate.of(1998, 6, 6))
                .build();

        Person expectedPerson = Person
                .builder()
                .name(person.getName())
                .identifier(StringUtils.removeAllExceptNumber(person.getIdentifier()))
                .birthDate(person.getBirthDate())
                .minInstallmentValue(BigDecimal.valueOf(1000))
                .maxLoanValue(BigDecimal.valueOf(100000))
                .identifierType(IdentifierType.PJ)
                .build();

        Mockito
                .when(repository.save(expectedPerson))
                .thenReturn(expectedPerson);

        service.create(person);

        Mockito
                .verify(repository)
                .save(expectedPerson);
    }

    @Test
    void create_withEUIdentifier() {
        Person person = Person
                .builder()
                .name(faker.name().fullName())
                .identifier("12345678")
                .birthDate(LocalDate.of(1998, 6, 6))
                .build();

        Person expectedPerson = Person
                .builder()
                .name(person.getName())
                .identifier(StringUtils.removeAllExceptNumber(person.getIdentifier()))
                .birthDate(person.getBirthDate())
                .minInstallmentValue(BigDecimal.valueOf(100))
                .maxLoanValue(BigDecimal.valueOf(10000))
                .identifierType(IdentifierType.EU)
                .build();

        Mockito
                .when(repository.save(expectedPerson))
                .thenReturn(expectedPerson);

        service.create(person);

        Mockito
                .verify(repository)
                .save(expectedPerson);
    }

    @Test
    void create_withAPIdentifier() {
        Person person = Person
                .builder()
                .name(faker.name().fullName())
                .identifier("1234567890")
                .birthDate(LocalDate.of(1998, 6, 6))
                .build();

        Person expectedPerson = Person
                .builder()
                .name(person.getName())
                .identifier(StringUtils.removeAllExceptNumber(person.getIdentifier()))
                .birthDate(person.getBirthDate())
                .minInstallmentValue(BigDecimal.valueOf(400))
                .maxLoanValue(BigDecimal.valueOf(25000))
                .identifierType(IdentifierType.AP)
                .build();

        Mockito
                .when(repository.save(expectedPerson))
                .thenReturn(expectedPerson);

        service.create(person);

        Mockito
                .verify(repository)
                .save(expectedPerson);
    }

    @Test
    void create_withInvalidIdentifier() {
        Person person = Person
                .builder()
                .name(faker.name().fullName())
                .identifier("1")
                .birthDate(LocalDate.of(1998, 6, 6))
                .build();

        Assertions.assertThrows(IdentifierTypeNotImplementedException.class, () -> {
            service.create(person);
        });

        Mockito
                .verify(repository, Mockito.times(0))
                .save(Mockito.any(Person.class));
    }

    @Test
    void findAll() {
        Person person = new Person();

        Mockito
                .when(repository.findAll())
                .thenReturn(List.of(person));

        service.findAll();

        Mockito
                .verify(repository)
                .findAll();
    }

    @Test
    void delete() throws Exception {
        UUID id = UUID.randomUUID();
        Person person = Person.builder().id(id).build();

        Mockito
                .when(repository.findById(id))
                .thenReturn(Optional.of(person));
        Mockito
                .doNothing()
                .when(repository)
                .delete(person);

        service.delete(id);

        Mockito
                .verify(repository)
                .findById(id);
        Mockito
                .verify(repository)
                .delete(person);
    }

    @Test
    void update() {
        UUID id = UUID.randomUUID();

        Person person = Person
                .builder()
                .id(id)
                .name(faker.name().fullName())
                .identifier(faker.cpf().valid())
                .birthDate(LocalDate.of(1998, 6, 6))
                .minInstallmentValue(BigDecimal.valueOf(300))
                .maxLoanValue(BigDecimal.valueOf(10000))
                .identifierType(IdentifierType.PF)
                .build();

        Person newPerson = Person
                .builder()
                .name(faker.name().fullName())
                .identifier(faker.cnpj().valid())
                .birthDate(LocalDate.of(1998, 6, 6))
                .build();

        Person expectedPerson = Person
                .builder()
                .id(id)
                .name(newPerson.getName())
                .identifier(StringUtils.removeAllExceptNumber(newPerson.getIdentifier()))
                .birthDate(newPerson.getBirthDate())
                .minInstallmentValue(BigDecimal.valueOf(1000))
                .maxLoanValue(BigDecimal.valueOf(100000))
                .identifierType(IdentifierType.PJ)
                .build();

        Mockito
                .when(repository.findById(id))
                .thenReturn(Optional.of(person));
        Mockito
                .when(repository.save(expectedPerson))
                .thenReturn(expectedPerson);

        service.update(id, newPerson);

        Mockito
                .verify(repository)
                .findById(id);
        Mockito
                .verify(repository)
                .save(expectedPerson);
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        Person person = new Person();

        Mockito
                .when(repository.findById(id))
                .thenReturn(Optional.of(person));

        service.findById(id);

        Mockito
                .verify(repository)
                .findById(id);
    }
}
