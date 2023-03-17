package com.lib.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.guieffect.qual.SafeEffect;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {

    private Integer books;
    private Integer authors;
    private Integer publishers;
    private Integer categories;
    private Integer loans;
    private Integer unReturnedBooks;
    private  Integer expiredBooks;
    private Integer members;



}
