package com.lib.controller;

import com.lib.domain.Book;
import com.lib.dto.AuthorDTO;
import com.lib.dto.BookDTO;
import com.lib.dto.request.BookRequest;
import com.lib.dto.request.BookUpdateRequest;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    @GetMapping("/visitors/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){

       BookDTO bookDTO = bookService.findById(id);
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping("/visitors")
    public ResponseEntity<Page<BookDTO>> getAllBooksWithPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam(value = "directon",required = false,defaultValue = "DESC")  Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));

        Page<BookDTO> bookDTOPage = bookService.findAllWithPage(pageable);
        return ResponseEntity.ok(bookDTOPage);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> deleteBook(@PathVariable("id") Long id){

        bookService.deleteBook(id);

        LibResponse response = new LibResponse(ResponseMessage.BOOK_DELETE_RESPONSE, true);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> updateBook(@PathVariable("id") Long id,
                                                  @RequestBody BookUpdateRequest updateRequest){

        bookService.updateBook(id, updateRequest);

        LibResponse response = new LibResponse(ResponseMessage.BOOK_UPDATE_RESPONSE, true);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }








}
