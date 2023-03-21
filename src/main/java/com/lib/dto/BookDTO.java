package com.lib.dto;


import com.lib.domain.Author;
import com.lib.domain.Category;
import com.lib.domain.ImageFile;


import com.lib.domain.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;
    private String name;
    private String isbn;
    private Integer pageCount;
    private Integer publishDate;
    private String shelfCode;
    private boolean active;
    private boolean featured;
    private boolean loanable;
    private LocalDateTime createDate;
    private boolean builtIn;
    private Set<String> imageFile;
    private Category category;
    private Publisher publisher;
    private Author author;

}
