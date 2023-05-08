package com.lib.service;

import com.lib.domain.Book;
import com.lib.domain.Loan;
import com.lib.domain.User;
import com.lib.dto.request.LoanCreateRequest;
import com.lib.dto.request.UpdateLoanRequest;
import com.lib.dto.response.*;
import com.lib.exception.BadRequestException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.BookMapper;
import com.lib.mapper.LoanMapper;
import com.lib.repository.LoanRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LoanService {

    private final LoanRepository loanRepository;

    private final UserService userService;
    private final BookService bookService;

    private final BookMapper bookMapper;
    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository, @Lazy UserService userService, BookService bookService, BookMapper bookMapper, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.loanMapper = loanMapper;
    }


    public LoanResponse createLoan(LoanCreateRequest loanCreateRequest) {


       User user = userService.getById(loanCreateRequest.getUserId());
       Book book = bookService.getBookById(loanCreateRequest.getBookId());


        LocalDateTime current=LocalDateTime.now();
        List<Loan> expiredLoans = loanRepository.findExpiredLoansBy(loanCreateRequest.getUserId(),current);
        List<Loan> activeLoansOfUser = loanRepository.findLoansByUserIdAndExpireDateIsNull(user);

        if(!book.isLoanable()) throw new BadRequestException(ErrorMessage.BOOK_IS_NOT_LOANABLE_MESSAGE);

        if(expiredLoans.size()>0) throw new IllegalStateException(ErrorMessage.BOOK_IS_NOT_HAVE_PERMISSON);

        Loan loan=new Loan();


        switch (user.getScore()) {
            case 2:
                loan.setLoanDate(current);
                loan.setExpireDate(current.plusDays(20));
                break;
            case 1:
                if (activeLoansOfUser.size() < 4) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(15));
                }
                break;
            case 0:
                if (activeLoansOfUser.size() < 3) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(10));
                }
                break;
            case -1:
                if (activeLoansOfUser.size() < 2) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(6));
                }
                break;
            case -2:
                if (activeLoansOfUser.size() < 1) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(3));
                }
                break;
            default: throw new BadRequestException("The user score is not between -2 and +2");
        }

        loan.setBook(book);
        loan.setUser(user);
        loan.setNotes(loanCreateRequest.getNotes());
        loanRepository.save(loan);
        book.setLoanable(false);
        bookService.save(book);

        LoanResponse loanResponse=new LoanResponse();
        loanResponse.setId(loan.getId());
        loanResponse.setUserId(loan.getUser().getId());
        loanResponse.setBookId(loan.getBook());
        return loanResponse;
    }





    public List<Loan> findByBookId(Long id) {
        List<Loan> loans =  loanRepository.findByBookId(id);
        return loans;
    }

    @Transactional(readOnly=true)
    public Page<Loan> findLoansWithPageByUserId(Pageable pageable) {

        User user = userService.getCurrentUser();

        return loanRepository.findAllWithPageByUserId(user.getId(), pageable);
    }


    public Page<Loan> getLoanedBookByBookId(Long bookId, Pageable pageable) {

        Book book = bookService.getBookById(bookId);

        return loanRepository.findAllByBookId(book, pageable);
    }


    public Page<LoanResponseBookUser> findAllLoansByUserId(Long userId, Pageable pageable) {

       User user = userService.getById(userId);

        Page<Loan> loanPage = loanRepository.findAllByUser(user, pageable);

       Page<LoanResponseBookUser> loanResponseBookUsers = loanPage.map(loan -> loanToLoanResponse(loan));
        return loanResponseBookUsers;
    }


    public LoanResponseBookUser loanToLoanResponse(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        LoanResponseBookUser loanResponseBookUser = new LoanResponseBookUser();

        loanResponseBookUser.setUserId(loan.getUser());
        loanResponseBookUser.setBookId(loan.getBook());

        return loanResponseBookUser;
    }


    public Loan getByIdAndUserId(Long loanId) {

        User user = userService.getCurrentUser();
        Loan loan = loanRepository.findByIdAndUserId(loanId, user.getId()).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.LOAN_NOT_FOUNT_EXCEPTION,loanId)));
        return loan;
    }


    public Loan getLoanById(Long userId) {

       Loan loan = loanRepository.findByUserId(userId).orElseThrow(()->
               new ResourceNotFoundException(String.format(ErrorMessage.LOAN_NOT_FOUNT_EXCEPTION,userId)));

       return loan;

    }


    public LoanUpdateResponse updateLoan(Long loanId, UpdateLoanRequest updateLoanRequest) {

        Loan loan = loanRepository.findById(loanId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.LOAN_NOT_FOUNT_EXCEPTION, loanId)));

        User user = loan.getUser();
        Book book = loan.getBook();

        try {
            if(updateLoanRequest.getReturnDate()!=null){
                book.setLoanable(true);
                loan.setReturnDate(updateLoanRequest.getReturnDate());
                bookService.save(book);
                loanRepository.save(loan);
                if(updateLoanRequest.getReturnDate().isEqual(loan.getReturnDate()) || updateLoanRequest.getReturnDate().isBefore(loan.getReturnDate())){
                    user.setScore(user.getScore()+1);
                    userService.save(user);
                    return new LoanUpdateResponse(loan);
                }else{
                    user.setScore(user.getScore()-1);
                    userService.save(user);
                    return new LoanUpdateResponse(loan);
                }
            }else{
                loan.setExpireDate(updateLoanRequest.getExpireDate());
                loan.setNotes(updateLoanRequest.getNotes());
                bookService.save(book);
                userService.save(user);
                loanRepository.save(loan);
                return new LoanUpdateResponse(loan);
            }
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            return new LoanUpdateResponse(loan);
        }
    }

    public Long allLoans() {
        return loanRepository.count();
    }


    public Long findLoanByReturnDateIsNull() {
        return loanRepository.findLoanByReturnDateIsNull().stream().count();
    }


    public Long ExpiredBooksCount() {
        LocalDateTime now = LocalDateTime.now();
       return loanRepository.findLoanByReturnDateIsNull().
                stream().filter(t -> t.getExpireDate().isBefore(now)).count();
    }


    public Page<BookResponse> WithPageExpiredBooks(Pageable pageable) {

            LocalDateTime now = LocalDateTime.now();

            List<Book> books = (loanRepository.findLoanByReturnDateIsNull().
                    stream().filter(t -> t.getExpireDate().isBefore(now)).map(t->t.getBook()).
                    collect(Collectors.toList()));

            Page<Book> bookPage = new PageImpl<>(books);

            Page<BookResponse> bookResponseList = bookPage.map(bookMapper::bookToBookResponse);

            return bookResponseList;
    }


    public Page ReportMostBorrowers(Pageable pageable) {

        Page findMostBorrowers = loanRepository.mostBorrowersResponses(pageable);

        return findMostBorrowers;
    }


    public List<Object> popularBooksReport(int amount, Pageable pageable) {

        Page<Object> mostPopularBooks = loanRepository.findMostPopularBooks(pageable);

        return mostPopularBooks.stream().limit(amount).collect(Collectors.toList());
    }
}
