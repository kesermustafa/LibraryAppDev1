package com.lib.service;

import com.lib.dto.response.ReportResponse;
import com.lib.exception.message.ErrorMessage;
import com.lib.report.ExcelReporter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final UserService userService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final LoanService loanService;


    public ReportService(UserService userService, AuthorService authorService, BookService bookService, LoanService loanService) {
        this.userService = userService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.loanService = loanService;
    }


    public ByteArrayInputStream getLibraryReport() {

       Long memberCount = userService.getAllUserCount();

        List<ReportResponse> response = new ArrayList<>();

      /*
       report.getBooks()
       report.getAuthors()
       report.getPublishers()
       report.getCategories()
       report.getLoans()
       report.getUnReturnedBooks()
       report.getExpiredBooks()
       report.getMembers()
      */


        try {
            return ExcelReporter.getLibraryExcelReport(response);
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.EXCEL_REPORT_ERROR_MESAGE);
        }


    }



}
