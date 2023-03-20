package com.lib.service;

import com.lib.domain.Book;
import com.lib.domain.Loan;
import com.lib.domain.User;
import com.lib.dto.LoanDTO;
import com.lib.dto.request.LoanRequest;

import com.lib.exception.BadRequestException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.LoanMapper;
import com.lib.repository.LoanRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;



import java.time.LocalDateTime;
import java.util.List;


@Service
public class LoanService {

    private final LoanRepository loanRepository;

    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    public Page<LoanDTO> getAllLoanWithPage(Pageable pageable) {
        Page<Loan> getAllLoan = loanRepository.findAll(pageable);

        return getAllLoan.map(map -> loanMapper.loanToLoanDTO(map));
    }


    public void createLoan(LoanRequest loanRequest, User user, Book book) {
        LocalDateTime returnDate = loanRequest.getReturnDate();
        LocalDateTime expireDate = loanRequest.getExpireDate();
        int score = user.getScore();

        checkBookIsAvailableAndLoanable(book);
        checkUserScoreForLoan(user, loanRequest.getLoanDate(), loanRequest.getExpireDate());

        Loan loan = loanMapper.loanRequestToLoan(loanRequest);

        if (returnDate.equals(expireDate) || returnDate.isBefore(expireDate)) {
            if (!(score > 2) && !(score < -2)) {
                user.setScore(score + 1);
            } else user.setScore(score - 1);

        }

        loan.setBook(book);
        loan.setUser(user);

        loanRepository.save(loan);
    }

    //Kitap müsait mi?
    //Kitap kiralanabilir mi?
    //Kaç kitap alınacak?
    //Kitap almak için puan yeterli mi?
    private boolean checkBookIsAvailableAndLoanable(Book book) {
        if (!book.isActive()) {
            throw new BadRequestException(ErrorMessage.BOOK_IS_NOT_AVAILABLE_MESSAGE);
        }
        if (!book.isLoanable()) {
            throw new BadRequestException(ErrorMessage.BOOK_IS_NOT_LOANABLE_MESSAGE);
        }
        return true;
    }

    private LocalDateTime checkUserScoreForLoan(User user, LocalDateTime loanDate,
                                                LocalDateTime finishDate) {

        switch (user.getScore()) {
            case -2 -> finishDate = loanDate.plusDays(3);
            case -1 -> finishDate = loanDate.plusDays(6);
            case 0 -> finishDate = loanDate.plusDays(10);
            case 1 -> finishDate = loanDate.plusDays(15);
            case 2 -> finishDate = loanDate.plusDays(20);
            default -> throw new BadRequestException(ErrorMessage.SCORE_IS_NOT_ENOUGH);
        }
        return finishDate;
    }


    public List<Loan> findByBookId(Long id) {

        List<Loan> loans =  loanRepository.findByBookId(id);
        return loans;
    }


}
