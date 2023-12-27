package com.wefin.wefin.domain;

import com.wefin.wefin.domain.enumeration.IdentifierType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "identifier", length = 50)
    private String identifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "identifier_type", length = 50)
    private IdentifierType identifierType;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "min_installment_value")
    private BigDecimal minInstallmentValue;

    @Column(name = "max_loan_value")
    private BigDecimal maxLoanValue;

    @ManyToOne
    private Person person;
}
