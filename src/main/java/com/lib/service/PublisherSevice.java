package com.lib.service;


import com.lib.domain.Book;
import com.lib.domain.Publisher;

import com.lib.dto.PublisherDTO;
import com.lib.dto.request.PublisherRequest;
import com.lib.exception.BadRequestException;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.mapper.PublisherMapper;
import com.lib.repository.PublisherRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherSevice {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;
    private final BookService bookService;

    private final UserService userService;

    public PublisherSevice(PublisherRepository publisherRepository,
                           PublisherMapper publisherMapper, @Lazy BookService bookService, UserService userService) {

        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
        this.bookService = bookService;
        this.userService = userService;
    }

    public void savePublisher(PublisherRequest publisherRequest) {

        Publisher publisher = new Publisher();

        publisher.setName(publisherRequest.getName());

        publisherRepository.save(publisher);
    }


    public Page<PublisherDTO> findAllWithPage(Pageable pageable) {
        Page<Publisher> publishers = publisherRepository.findAll(pageable);
       return getPublisherDTOPage(publishers);
    }


    private Page<PublisherDTO> getPublisherDTOPage(Page<Publisher> publisherPage){
        return publisherPage.map(
                publisher -> publisherMapper.publisherToPublisherDTO(publisher));
    }

    public PublisherDTO getPublisherById(Long id) {

        Publisher publisher = publisherFindById(id);

        return publisherMapper.publisherToPublisherDTO(publisher);
    }


    public Publisher publisherFindById(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUNT_EXCEPTION, id)));
        return publisher;
    }


    public void updatePublisher(Long id, PublisherRequest publisherRequest) {

        Publisher publisher = publisherFindById(id);

        publisher.setName(publisherRequest.getName());
        publisher.setBuiltIn(publisherRequest.getBuiltIn());

        publisherRepository.save(publisher);
    }


    public void deletePublisher(Long id) {

        Publisher publisher = publisherFindById(id);

        if (publisher.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        publisherRepository.delete(publisher);
    }

    public Long allPublisher() {
        return publisherRepository.count();
    }





}
