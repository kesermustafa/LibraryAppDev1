package com.lib.controller;

import com.lib.domain.Book;
import com.lib.domain.Loan;
import com.lib.domain.User;
import com.lib.dto.LoanDTO;
import com.lib.dto.request.LoanCreateRequest;
import com.lib.dto.request.UpdateLoanRequest;
import com.lib.dto.response.*;
import com.lib.service.BookService;
import com.lib.service.LoanService;
import com.lib.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;

    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody LoanCreateRequest loanCreateRequest){

       LoanResponse loanResponse = loanService.createLoan(loanCreateRequest);

        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or hasRole('MEMBER')")
    @GetMapping()
    public ResponseEntity<Page<Loan>>getLoansWithPageByUserId(@RequestParam ("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sort") String prop,
                                                              @RequestParam("direction") Sort.Direction direction){

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
        Page<Loan>loans=loanService.findLoansWithPageByUserId(pageable);
        return new ResponseEntity<>(loans,HttpStatus.OK);
    }




    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/book/{bookId}")
    public ResponseEntity<Page<Loan>> findLoanedBookByBookId(@PathVariable Long bookId,
                                                             @RequestParam ("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam("sort") String prop,
                                                             @RequestParam("direction") Sort.Direction direction){

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
        Page<Loan> loans = loanService.getLoanedBookByBookId(bookId,pageable);

        return new ResponseEntity<>(loans,HttpStatus.OK);

    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<LoanResponseBookUser>> findAllLoansByUserId(@PathVariable Long userId,
                                                                           @RequestParam ("page") int page,
                                                                           @RequestParam("size") int size,
                                                                           @RequestParam("sort") String prop,
                                                                           @RequestParam("direction") Sort.Direction direction){
        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));

        Page<LoanResponseBookUser> loans = loanService.findAllLoansByUserId(userId,pageable);
        return new ResponseEntity<>(loans, HttpStatus.OK);

    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or hasRole('MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable(value="id") Long loanId){

        Loan loan = loanService.getByIdAndUserId(loanId);

        return new ResponseEntity<>(loan, HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/auth/{userId}")
    public ResponseEntity<Loan> getLoanWithByUserId(@PathVariable Long userId){

        Loan loan = loanService.getLoanById(userId);
        return new ResponseEntity<>(loan, HttpStatus.OK);

    }




    @PutMapping("/loans/{id}")
    @PreAuthorize("hasRole('ADMIN') or  hasRole('EMPLOYEE')")
    public ResponseEntity<LoanUpdateResponse> updateLoan(@PathVariable(value = "id") Long loanId,
                                                         @Valid @RequestBody UpdateLoanRequest updateLoanRequest) {

        LoanUpdateResponse updatedLoanResponse = loanService.updateLoan(loanId, updateLoanRequest);
        return new ResponseEntity<>(updatedLoanResponse, HttpStatus.OK);
    }






}
