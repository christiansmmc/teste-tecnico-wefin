package com.wefin.wefin.domain;

import com.wefin.wefin.domain.enumeration.IdentifierType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "person")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!Objects.equals(id, person.id)) return false;
        if (!Objects.equals(name, person.name)) return false;
        if (!Objects.equals(identifier, person.identifier)) return false;
        if (!Objects.equals(birthDate, person.birthDate)) return false;
        if (!Objects.equals(minInstallmentValue, person.minInstallmentValue))
            return false;
        if (!Objects.equals(maxLoanValue, person.maxLoanValue))
            return false;
        return identifierType == person.identifierType;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (minInstallmentValue != null ? minInstallmentValue.hashCode() : 0);
        result = 31 * result + (maxLoanValue != null ? maxLoanValue.hashCode() : 0);
        result = 31 * result + (identifierType != null ? identifierType.hashCode() : 0);
        return result;
    }
}
