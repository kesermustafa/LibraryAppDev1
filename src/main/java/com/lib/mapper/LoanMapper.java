package com.lib.mapper;

import com.lib.domain.Loan;
import com.lib.dto.LoanDTO;
import com.lib.dto.request.LoanRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    LoanDTO loanToLoanDTO(Loan loan);

    Loan loanRequestToLoan(LoanRequest loanRequest);

    List<LoanDTO> loanListToLoanDTOList(List<Loan> loan);


}
