package com.wefin.wefin.facade.dto.loan;

import com.wefin.wefin.domain.enumeration.PaymentStatus;
import com.wefin.wefin.facade.dto.person.PersonIdDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanDTO {

    private UUID id;

    private BigDecimal loanValue;

    private Long installmentsTotal;

    private PaymentStatus paymentStatus;

    private LocalDateTime createdDate;

    private PersonIdDTO person;
}
