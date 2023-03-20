package com.lib.controller;

import com.lib.domain.Author;
import com.lib.domain.Book;
import com.lib.dto.AuthorDTO;
import com.lib.dto.request.AuthorRequest;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.mapper.AuthorMapper;
import com.lib.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping("/visitors")
    public ResponseEntity<Page<AuthorDTO>> getAllAuthorWithPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam(value = "directon",required = false,defaultValue = "DESC")  Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));

        Page<AuthorDTO> authorDTOPage = authorService.findAllWithPage(pageable);
        return ResponseEntity.ok(authorDTOPage);

    }


    @GetMapping("/visitors/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Long id){

        AuthorDTO authorDTO = authorService.getAuthorById(id);

        return ResponseEntity.ok(authorDTO);

    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> createAuthor(@Valid @RequestBody AuthorRequest authorRequest){

        authorService.createAuthor(authorRequest);

        LibResponse response = new LibResponse(ResponseMessage.AUTHOR_CREATED_RESPONSE_MESSAGE,true);

        return ResponseEntity.ok(response);

    }



    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> updateAuthor(@PathVariable("id") Long id, @Valid @RequestBody AuthorRequest authorRequest){

        authorService.updateAuthor(id,authorRequest);

        LibResponse response = new LibResponse(ResponseMessage.AUTHOR_UPDATE_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> deleteAuthor(@PathVariable("id") Long id){

        authorService.removeAuthor(id);

        LibResponse response = new LibResponse(ResponseMessage.AUTHOR_DELETE_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(response);

    }

    // Yazara ait kitaplar
    @GetMapping("/visitors/books/{authorId}")
    public ResponseEntity<List<Book>> getAuthorBooks(@PathVariable("authorId") Long authorId){

        List<Book> bookList = authorService.getAuthorBooks(authorId);
        return ResponseEntity.ok(bookList);
    }


}