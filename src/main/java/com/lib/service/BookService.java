package com.lib.service;

import com.lib.domain.*;
import com.lib.dto.request.BookRequest;
import com.lib.exception.ConflictException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublisherSevice publisherSevice;
    private final CategoryService categoryService;
    private final ImageFileService imageFileService;


    public BookService(BookRepository bookRepository, AuthorService authorService,
                       PublisherSevice publisherSevice, CategoryService categoryService,
                       ImageFileService imageFileService) {

        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.publisherSevice = publisherSevice;
        this.categoryService = categoryService;
        this.imageFileService = imageFileService;
    }



    public Book getBookById(Long bookId){

        Book book=bookRepository.findById(bookId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUNT_EXCEPTION,bookId)));
        return book;
    }

    public void saveBook(BookRequest bookRequest) {

        ImageFile imageFile = null;

        if(bookRequest.getImageId() != null){
            imageFile = imageFileService.findImageById(bookRequest.getImageId());
        }

        if(imageFile != null){
            Integer usedBookCount = bookRepository.findBookCountByImageId(imageFile.getId());
            if(usedBookCount>0){
                throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
            }
        }

        Book book = new Book();

        Set<ImageFile> imageFiles = new HashSet<>();
        imageFiles.add(imageFile);
        book.setImageFile(imageFiles);

        Author author = authorService.getById(bookRequest.getAuthorId());
        Publisher publisher = publisherSevice.publisherFindById(bookRequest.getPublisherId());
        Category category = categoryService.findById(bookRequest.getCategoryId());

        book.setAuthor(author);
        book.setCategory(category);
        book.setPublisher(publisher);
        book.setName(bookRequest.getName());
        book.setIsbn(bookRequest.getIsbn());
        book.setPageCount(bookRequest.getPageCount());
        book.setPublishDate(bookRequest.getPublishDate());
        book.setShelfCode(bookRequest.getShelfCode());
        book.setFeatured(bookRequest.isFeatured());

        bookRepository.save(book);

    }





}
