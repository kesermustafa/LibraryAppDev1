package com.lib.dto;


import com.lib.domain.ImageFile;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long authorId;
    private Long publisherId;
    private Integer publishDate;
    private Long categoryId;
    private Set<ImageFile> imageFile;
    private String shelfCode;
    private boolean active;
    private boolean featured;
    private boolean loanable;
    private LocalDateTime createDate;
    private boolean builtIn;

}
