package com.wefin.wefin.facade.dto.person;

import com.wefin.wefin.domain.enumeration.IdentifierType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PersonDTO {

    private UUID id;

    private String name;

    private String identifier;

    private IdentifierType identifierType;

    private LocalDate birthDate;

    private BigDecimal minInstallmentValue;

    private BigDecimal maxLoanValue;
}
