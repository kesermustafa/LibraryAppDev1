package com.lib.dto.response;

import com.lib.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookExcelReport {

    private Long id;
    private String name;
    private String isbn;
    private Integer pageCount;
    private Integer publishDate;
    private String shelfCode;
    private LocalDateTime createDate;
    private String categoryName;
    private String publisherName;
    private String authorName;
    private boolean active;
    private boolean featured;
    private boolean loanable;

    public BookExcelReport(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.isbn = getIsbn();
        this.pageCount = book.getPageCount();
        this.publishDate = book.getPublishDate();
        this.shelfCode = book.getShelfCode();
        this.createDate = book.getCreateDate();
        this.categoryName = book.getCategory().getName();
        this.publisherName = book.getPublisher().getName();
        this.authorName = book.getAuthor().getName();
        this.active = book.isActive();
        this.featured = book.isFeatured();
        this.loanable = book.isLoanable();
    }

}
