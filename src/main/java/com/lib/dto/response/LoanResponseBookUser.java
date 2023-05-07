package com.lib.dto.response;

import com.lib.domain.Book;
import com.lib.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseBookUser {


    public Long id;
    public User userId;
    public Book bookId;


}
