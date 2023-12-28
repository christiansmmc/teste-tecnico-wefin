package com.wefin.wefin.facade;

import com.wefin.wefin.domain.Loan;
import com.wefin.wefin.facade.dto.loan.LoanDTO;
import com.wefin.wefin.facade.dto.loan.LoanToCreateDTO;
import com.wefin.wefin.facade.mapper.LoanMapper;
import com.wefin.wefin.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanFacade {

    private final LoanMapper mapper;
    private final LoanService service;

    public LoanFacade(
            LoanMapper mapper,
            LoanService service
    ) {
        this.mapper = mapper;
        this.service = service;
    }

    @Transactional
    public LoanDTO create(LoanToCreateDTO dto) {
        Loan loan = mapper.toEntity(dto);
        return mapper.toDto(service.create(loan));
    }
}
