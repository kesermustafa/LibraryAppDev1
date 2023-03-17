package com.lib.mapper;

import com.lib.domain.Book;
import com.lib.dto.BookDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO bookToBookDTO(Book book);

    List<BookDTO> map(List<Book> books);



}
