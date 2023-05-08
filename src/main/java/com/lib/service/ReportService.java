package com.lib.service;

import com.lib.domain.Book;
import com.lib.dto.response.BookResponse;
import com.lib.dto.response.MostBorrowersResponse;
import com.lib.dto.response.ReportResponse;
import com.lib.exception.message.ErrorMessage;
import com.lib.report.ExcelReporter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final UserService userService;
    private final AuthorService authorService;
    private final PublisherSevice publisherSevice;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final LoanService loanService;
    private final RoleService roleService;


    public ReportService(UserService userService, AuthorService authorService,
                         PublisherSevice publisherSevice, CategoryService categoryService,
                         BookService bookService, LoanService loanService, RoleService roleService) {
        this.userService = userService;
        this.authorService = authorService;
        this.publisherSevice = publisherSevice;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.loanService = loanService;
        this.roleService = roleService;
    }


    public ReportResponse getReportAboutAllData() {

        ReportResponse report = new ReportResponse();

        Long bookCount = bookService.allBook();
        report.setBooks(bookCount);

        Long authorCount = authorService.allAuthor();
        report.setAuthors(authorCount);

        Long publisherCount = publisherSevice.allPublisher();
        report.setPublishers(publisherCount);

        Long categoryCount = categoryService.allCategory();
        report.setCategories(categoryCount);

        Long loansCount = loanService.allLoans();
        report.setLoans(loansCount);

        Long UnReturnedBooksCount = loanService.findLoanByReturnDateIsNull();
        report.setUnReturnedBooks(UnReturnedBooksCount);

        Long ExpiredBooksCount = loanService.ExpiredBooksCount();
        report.setExpiredBooks(ExpiredBooksCount);

        Long memberCount = roleService.memberCount();

        report.setMembers(memberCount);

        return report;


    }


    public Page<BookResponse> findReportsWithPage(Pageable pageable) {

        Page<BookResponse> books = bookService.findBookByLoanableIsFalse(pageable);
        return books;
    }


    public Page<BookResponse> findReportsWithPageExpiredBooks(Pageable pageable) {

        Page<BookResponse> bookResponsePage = loanService.WithPageExpiredBooks(pageable);

        return bookResponsePage;

    }


    public Page findReportMostBorrowers(Pageable pageable) {

        Page mostBorrowersResponses = loanService.ReportMostBorrowers(pageable);

        return mostBorrowersResponses;
    }

    public List<Object> findReportMostPopularBooks(int amount, Pageable pageable) {

        List<Object> mostPopularBooks = loanService.popularBooksReport(amount, pageable);

        return mostPopularBooks;
    }



}
