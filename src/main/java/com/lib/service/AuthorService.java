package com.lib.service;

import com.lib.domain.Author;
import com.lib.domain.Book;
import com.lib.dto.AuthorDTO;
import com.lib.dto.request.AuthorRequest;
import com.lib.exception.BadRequestException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.AuthorMapper;
import com.lib.repository.AuthorRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookService bookService;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, @Lazy BookService bookService, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
        this.authorMapper = authorMapper;
    }

    public void createAuthor(AuthorRequest authorRequest) {

        Author author = new Author();
        author.setName(authorRequest.getName());

        authorRepository.save(author);

    }

    public AuthorDTO getAuthorById(Long id) {

        Author author = authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURSE_NOT_FOUND_EXCEPTION,id)));

        return authorMapper.authorToAuthorDTO(author);
    }

    public Author getById(Long id) {

        Author author = authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURSE_NOT_FOUND_EXCEPTION,id)));

        return author;
    }



    public void updateAuthor(Long id, AuthorRequest authorRequest) {

        Author author = getById(id);

        if (author.isBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        author.setName(authorRequest.getName());

        authorRepository.save(author);

    }

    public void removeAuthor(Long id) {
        Author author = getById(id);

        if (author.isBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        authorRepository.delete(author);
    }

    private Page<AuthorDTO> getUserDTOPage(Page<Author> authorPage){
        return authorPage.map(
                author -> authorMapper.authorToAuthorDTO(author));
    }

    public Page<AuthorDTO> findAllWithPage(Pageable pageable) {
        Page<Author> authorPage = authorRepository.findAll(pageable);

        return getUserDTOPage(authorPage);
    }




}
