package com.wefin.wefin.facade.dto.loan;

import com.wefin.wefin.facade.dto.person.PersonIdDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanToCreateDTO {

    @NotNull
    private BigDecimal loanValue;

    @NotNull
    private Long installmentsTotal;

    @Valid
    @NotNull
    private PersonIdDTO person;
}
