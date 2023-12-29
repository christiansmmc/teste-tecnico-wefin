package com.wefin.wefin.service;

import com.wefin.wefin.domain.Loan;
import com.wefin.wefin.domain.Person;
import com.wefin.wefin.domain.enumeration.IdentifierType;
import com.wefin.wefin.domain.enumeration.PaymentStatus;
import com.wefin.wefin.exception.InvalidIdentifierException;
import com.wefin.wefin.exception.InvalidInstallmentNumberException;
import com.wefin.wefin.exception.InvalidLoanValueException;
import com.wefin.wefin.repository.LoanRepository;
import com.wefin.wefin.service.impl.LoanServiceImpl;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository repository;
    @Mock
    private PersonService personService;
    @Mock
    private PaymentService paymentService;

    private LoanService service;

    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        service = new LoanServiceImpl(
                repository,
                personService,
                paymentService
        );
    }

    @Test
    void create_withCpfAndAllValid() {
        Loan loan = this.createLoan(UnaryOperator.identity());
        Person person = loan.getPerson();

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);
        Mockito
                .when(repository.saveAndFlush(Mockito.any(Loan.class)))
                .thenReturn(loan);
        Mockito
                .doNothing()
                .when(paymentService)
                .makePayment(loan.getId());

        service.create(loan);

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository)
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService)
                .makePayment(loan.getId());
    }

    @Test
    void create_withCnpjAndAllValid() {
        Person person = this.createPerson(it ->
                it
                        .minInstallmentValue(BigDecimal.valueOf(1000))
                        .maxLoanValue(BigDecimal.valueOf(100000))
                        .identifier(StringUtils.removeAllExceptNumber(faker.cnpj().valid()))
                        .identifierType(IdentifierType.PJ)
        );

        Loan loan = this.createLoan(it -> it.person(person));

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);
        Mockito
                .when(repository.saveAndFlush(Mockito.any(Loan.class)))
                .thenReturn(loan);
        Mockito
                .doNothing()
                .when(paymentService)
                .makePayment(loan.getId());

        service.create(loan);

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository)
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService)
                .makePayment(loan.getId());
    }

    @Test
    void create_withStudentAndAllValid() {
        Person person = this.createPerson(it ->
                it
                        .minInstallmentValue(BigDecimal.valueOf(100))
                        .maxLoanValue(BigDecimal.valueOf(10000))
                        .identifier("12345678")
                        .identifierType(IdentifierType.EU)
        );

        Loan loan = this.createLoan(it -> it.person(person));

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);
        Mockito
                .when(repository.saveAndFlush(Mockito.any(Loan.class)))
                .thenReturn(loan);
        Mockito
                .doNothing()
                .when(paymentService)
                .makePayment(loan.getId());

        service.create(loan);

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository)
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService)
                .makePayment(loan.getId());
    }

    @Test
    void create_withRetireeAndAllValid() {
        Person person = this.createPerson(it ->
                it
                        .minInstallmentValue(BigDecimal.valueOf(400))
                        .maxLoanValue(BigDecimal.valueOf(25000))
                        .identifier("1234567890")
                        .identifierType(IdentifierType.AP)
        );

        Loan loan = this.createLoan(it -> it.person(person));

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);
        Mockito
                .when(repository.saveAndFlush(Mockito.any(Loan.class)))
                .thenReturn(loan);
        Mockito
                .doNothing()
                .when(paymentService)
                .makePayment(loan.getId());

        service.create(loan);

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository)
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService)
                .makePayment(loan.getId());
    }

    @Test
    void create_withInvalidCpf() {
        Person person = this.createPerson(it ->
                it
                        .identifier(StringUtils.removeAllExceptNumber(faker.cpf().invalid()))
                        .identifierType(IdentifierType.PF)
        );

        Loan loan = this.createLoan(it -> it.person(person));

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);

        Assertions.assertThrows(InvalidIdentifierException.class, () -> {
            service.create(loan);
        });

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository, Mockito.times(0))
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService, Mockito.times(0))
                .makePayment(Mockito.any());
    }

    @Test
    void create_withInvalidCnpj() {
        Person person = this.createPerson(it ->
                it
                        .identifier(StringUtils.removeAllExceptNumber(faker.cnpj().invalid()))
                        .identifierType(IdentifierType.PJ)
        );

        Loan loan = this.createLoan(it -> it.person(person));

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);

        Assertions.assertThrows(InvalidIdentifierException.class, () -> {
            service.create(loan);
        });

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository, Mockito.times(0))
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService, Mockito.times(0))
                .makePayment(Mockito.any());
    }

    @Test
    void create_withInvalidStudent() {
        Person person = this.createPerson(it ->
                it
                        .identifier("12345671")
                        .identifierType(IdentifierType.EU)
        );

        Loan loan = this.createLoan(it -> it.person(person));

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);

        Assertions.assertThrows(InvalidIdentifierException.class, () -> {
            service.create(loan);
        });

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository, Mockito.times(0))
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService, Mockito.times(0))
                .makePayment(Mockito.any());
    }

    @Test
    void create_withInvalidRetiree() {
        Person person = this.createPerson(it ->
                it
                        .identifier("1111111111")
                        .identifierType(IdentifierType.AP)
        );

        Loan loan = this.createLoan(it -> it.person(person));

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);

        Assertions.assertThrows(InvalidIdentifierException.class, () -> {
            service.create(loan);
        });

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository, Mockito.times(0))
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService, Mockito.times(0))
                .makePayment(Mockito.any());
    }

    @Test
    void create_amountIsHigherThanAllowed() {
        Loan loan = this.createLoan(it -> it.loanValue(BigDecimal.valueOf(999999999)));
        Person person = loan.getPerson();

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);

        Assertions.assertThrows(InvalidLoanValueException.class, () -> {
            service.create(loan);
        });

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository, Mockito.times(0))
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService, Mockito.times(0))
                .makePayment(loan.getId());
    }

    @Test
    void create_installmentMinValueIsLowerThanAllowed() {
        Loan loan = this.createLoan(it ->
                it
                        .loanValue(BigDecimal.valueOf(1000))
                        .installmentsTotal(24L)
        );
        Person person = loan.getPerson();

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);

        Assertions.assertThrows(InvalidInstallmentNumberException.class, () -> {
            service.create(loan);
        });

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository, Mockito.times(0))
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService, Mockito.times(0))
                .makePayment(loan.getId());
    }

    @Test
    void create_installmentNumberIsHigherThanAllower() {
        Loan loan = this.createLoan(it ->
                it
                        .installmentsTotal(25L)
        );
        Person person = loan.getPerson();

        Mockito
                .when(personService.findById(loan.getPerson().getId()))
                .thenReturn(person);

        Assertions.assertThrows(InvalidInstallmentNumberException.class, () -> {
            service.create(loan);
        });

        Mockito
                .verify(personService)
                .findById(loan.getPerson().getId());
        Mockito
                .verify(repository, Mockito.times(0))
                .saveAndFlush(Mockito.any(Loan.class));
        Mockito
                .verify(paymentService, Mockito.times(0))
                .makePayment(loan.getId());
    }

    @Test
    void findById() {
        Loan loan = this.createLoan(UnaryOperator.identity());

        Mockito
                .when(repository.findById(loan.getId()))
                .thenReturn(Optional.of(loan));

        service.findById(loan.getId());

        Mockito
                .verify(repository)
                .findById(loan.getId());
    }

    @Test
    void save() {
        Loan loan = this.createLoan(UnaryOperator.identity());

        Mockito
                .when(repository.save(loan))
                .thenReturn(loan);

        service.save(loan);

        Mockito
                .verify(repository)
                .save(loan);
    }

    private Person createPerson(UnaryOperator<Person.PersonBuilder> builder) {
        Person.PersonBuilder personBuilder = Person
                .builder()
                .id(UUID.randomUUID())
                .name(faker.name().fullName())
                .identifier(StringUtils.removeAllExceptNumber(faker.cpf().valid()))
                .birthDate(LocalDate.now())
                .minInstallmentValue(BigDecimal.valueOf(300))
                .maxLoanValue(BigDecimal.valueOf(10000))
                .identifierType(IdentifierType.PF);

        return builder.apply(personBuilder).build();
    }

    private Loan createLoan(UnaryOperator<Loan.LoanBuilder> builder) {
        Loan.LoanBuilder loanBuilder = Loan
                .builder()
                .id(UUID.randomUUID())
                .loanValue(BigDecimal.valueOf(1000))
                .installmentsTotal(1L)
                .paymentStatus(PaymentStatus.PENDING)
                .createdDate(LocalDateTime.now())
                .person(this.createPerson(UnaryOperator.identity()));

        return builder.apply(loanBuilder).build();
    }
}
