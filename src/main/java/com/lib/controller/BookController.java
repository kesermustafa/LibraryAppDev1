package com.lib.controller;

import com.lib.dto.request.BookRequest;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> bookSave(@Valid @RequestBody BookRequest bookRequest){

        bookService.saveBook(bookRequest);

        LibResponse response = new LibResponse(ResponseMessage.BOOK_CREATED_RESPONSE, true);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }



}
