package com.lib.mapper;

import com.lib.domain.Author;
import com.lib.domain.Publisher;
import com.lib.dto.AuthorDTO;
import com.lib.dto.PublisherDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    PublisherDTO publisherToPublisherDTO(Publisher publisher);


    List<PublisherDTO> map(List<Publisher> publishers);

}
