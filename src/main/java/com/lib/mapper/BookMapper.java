package com.lib.mapper;

import com.lib.domain.Book;
import com.lib.domain.ImageFile;
import com.lib.dto.BookDTO;
import com.lib.dto.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "imageFile", target = "imageFile", qualifiedByName = "getImageAsString")
    BookDTO bookToBookDTO(Book book);

   // List<BookDTO> map(List<Book> books);



    @Named("getImageAsString")
    public static Set<String> getImageIds(Set<ImageFile> imageFiles){

        Set<String> imgs = new HashSet<>();

        imgs = imageFiles.stream().
                map(imFile->imFile.getId().toString()).
                collect(Collectors.toSet());

        return imgs;
    }


    BookResponse bookToBookResponse(Book book);


    List<BookResponse> map(List<Book> book);

}
