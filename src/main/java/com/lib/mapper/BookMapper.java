package com.lib.mapper;

import com.lib.domain.*;
import com.lib.dto.BookDTO;
import com.lib.dto.response.BookExcelReport;
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

    List<BookDTO> maping(List<Book> books);



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


    @Mapping(source = "book.author", target = "authorName", qualifiedByName = "getAuthorName")
    @Mapping(source = "book.publisher", target = "publisherName", qualifiedByName = "getPublisherName")
    @Mapping(source = "book.category", target = "categoryName", qualifiedByName = "getCategoryName")
    BookExcelReport bookToBookExcelReport(Book book);

    List<BookExcelReport> mapl(List<Book> book);


    @Named("getAuthorName")
    public static String getAuthorName(Author author){
        return author.getName();
    }
   @Named("getPublisherName")
    public static String getPublisherName(Publisher publisher){
        return publisher.getName();
    }

    @Named("getCategoryName")
    public static String getCategoryName(Category category){
        return category.getName();
    }



}
