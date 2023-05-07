package com.lib.mapper;

import com.lib.domain.Loan;
import com.lib.dto.LoanDTO;
import com.lib.dto.request.LoanCreateRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    LoanDTO loanToLoanDTO(Loan loan);

    Loan loanRequestToLoan(LoanCreateRequest loanCreateRequest);

    List<LoanDTO> loanListToLoanDTOList(List<Loan> loan);


}
