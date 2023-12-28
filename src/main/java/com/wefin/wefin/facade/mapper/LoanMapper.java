package com.wefin.wefin.facade.mapper;


import com.wefin.wefin.domain.Loan;
import com.wefin.wefin.facade.dto.loan.LoanDTO;
import com.wefin.wefin.facade.dto.loan.LoanToCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface LoanMapper {

    Loan toEntity(LoanToCreateDTO dto);

    LoanDTO toDto(Loan loan);
}
