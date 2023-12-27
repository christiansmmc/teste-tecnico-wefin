package com.wefin.wefin.domain;

import com.wefin.wefin.domain.enumeration.IdentifierType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "identifier", length = 50)
    private String identifier;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "min_installment_value")
    private BigDecimal minInstallmentValue;

    @Column(name = "max_loan_value")
    private BigDecimal maxLoanValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "identifier_type", length = 50)
    private IdentifierType identifierType;
}
