package com.lib.dto;

import com.lib.domain.Book;
import com.lib.domain.Loan;
import com.lib.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {

    private Long id;
    private LocalDateTime loanDate;
    private LocalDateTime expireDate;
    private LocalDateTime returnDate;
    private String notes;
    private Long bookId;
    private Long userId;


    public LoanDTO(Loan loan){
        this.id=loan.getId();
        this.loanDate=loan.getLoanDate();
        this.expireDate=loan.getExpireDate();
        this.returnDate=loan.getReturnDate();
        this.notes=loan.getNotes();
        this.userId= loan.getUser().getId();
        this.bookId= loan.getBook().getId();
    }

}
