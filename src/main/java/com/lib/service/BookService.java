package com.lib.service;

import com.lib.domain.*;
import com.lib.dto.BookDTO;
import com.lib.dto.request.BookRequest;
import com.lib.dto.request.BookUpdateRequest;
import com.lib.exception.BadRequestException;
import com.lib.exception.ConflictException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.BookMapper;
import com.lib.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublisherSevice publisherSevice;
    private final CategoryService categoryService;
    private final ImageFileService imageFileService;
    private final LoanService loanService;
    private final BookMapper bookMapper;


    public BookService(BookRepository bookRepository, AuthorService authorService,
                       PublisherSevice publisherSevice, CategoryService categoryService,
                       ImageFileService imageFileService, LoanService loanService, BookMapper bookMapper) {

        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.publisherSevice = publisherSevice;
        this.categoryService = categoryService;
        this.imageFileService = imageFileService;
        this.loanService = loanService;
        this.bookMapper = bookMapper;
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


    public BookDTO findById(Long id) {

        Book book = getBookById(id);
        BookDTO bookDTO = bookToBookDTO(book);
        return bookDTO;
    }


    public BookDTO bookToBookDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        bookDTO.setId( book.getId() );
        bookDTO.setName( book.getName() );
        bookDTO.setIsbn( book.getIsbn() );
        bookDTO.setPageCount( book.getPageCount() );
        bookDTO.setPublishDate( book.getPublishDate() );

        Set<ImageFile> set = book.getImageFile();
        if ( set != null ) {
            bookDTO.setImageFile( new LinkedHashSet<ImageFile>( set ) );
        }
        bookDTO.setShelfCode( book.getShelfCode() );
        bookDTO.setActive( book.isActive() );
        bookDTO.setFeatured( book.isFeatured() );
        bookDTO.setLoanable( book.isLoanable() );
        bookDTO.setCreateDate( book.getCreateDate() );
        bookDTO.setBuiltIn( book.isBuiltIn() );
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setCategory(book.getCategory());

        return bookDTO;
    }


    public List<BookDTO> map(List<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<BookDTO> list = new ArrayList<BookDTO>( books.size() );
        for ( Book book : books ) {
            list.add( bookToBookDTO( book ) );
        }
        return list;
    }

    public Page<BookDTO> findAllWithPage(Pageable pageable) {

        Page<Book> bookPage = bookRepository.findAll(pageable);
        return getUserDTOPage(bookPage);
    }


    private Page<BookDTO> getUserDTOPage(Page<Book> bookPage){
        return bookPage.map(
                book -> bookToBookDTO(book)
        );
    }


    public void deleteBook(Long id) {

        Book book = getBookById(id);

        if(book.isBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

       List<Loan> loans =loanService.findByBookId(book.getId());
        if(loans !=null){
            for ( Loan w : loans ) {
                if (w.getReturnDate() == null){
                    throw new BadRequestException(ErrorMessage.NOT_DELETED_METHOD_MESSAGE);
                }
            }
        }

        bookRepository.delete(book);
    }


    public void updateBook(Long id, BookUpdateRequest updateRequest) {

        Book book = getBookById(id);

        if(updateRequest.getImageId() !=null) {

            ImageFile imageFile = imageFileService.findImageById(updateRequest.getImageId());

            if (!book.getImageFile().contains(updateRequest.getImageId())) {
                Integer usedBookCount = bookRepository.findBookCountByImageId(imageFile.getId());
                if (usedBookCount > 0) {
                    throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
                }
            }

            Set<ImageFile> imageFiles = new HashSet<>();
            imageFiles.add(imageFile);
            book.setImageFile(imageFiles);
        }else{
            book.setImageFile(book.getImageFile());
        }


        Author author = authorService.getById(updateRequest.getAuthorId());
        Publisher publisher = publisherSevice.publisherFindById(updateRequest.getPublisherId());
        Category category = categoryService.findById(updateRequest.getCategoryId());

        book.setName(updateRequest.getName());
        book.setIsbn(updateRequest.getIsbn());
        book.setPageCount(updateRequest.getPageCount());
        book.setPublishDate(updateRequest.getPublishDate());
        book.setShelfCode(updateRequest.getShelfCode());
        book.setFeatured(updateRequest.isFeatured());
        book.setActive(updateRequest.isActive());
        book.setLoanable(updateRequest.isLoanable());
        book.setBuiltIn(updateRequest.isBuiltIn());
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);

        bookRepository.save(book);
    }


    public List<Book> getAuthorBooks(Long authorId) {

        List<Book>  bookList = bookRepository.findAllByAuthorId(authorId);

        return bookList;
    }


}
