package com.lib.controller;


import com.lib.dto.PublisherDTO;
import com.lib.dto.request.PublisherRequest;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.PublisherSevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherSevice publisherSevice;

    public PublisherController(PublisherSevice publisherSevice) {
        this.publisherSevice = publisherSevice;
    }


    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> savePublisher(@Valid @RequestBody PublisherRequest publisherRequest){

        publisherSevice.savePublisher(publisherRequest);

        LibResponse response = new LibResponse(ResponseMessage.PUBLISHER_CREATED_RESPONSE_MESSAGE, true);

        return ResponseEntity.ok(response);

    }


    @GetMapping("/visitors")
    public ResponseEntity<Page<PublisherDTO>> getAllAuthorWithPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String prop,
            @RequestParam(value = "directon",required = false,defaultValue = "DESC")  Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));

        Page<PublisherDTO> publisherDTOS = publisherSevice.findAllWithPage(pageable);
        return ResponseEntity.ok(publisherDTOS);
    }

    @GetMapping("/visitors/{id}")
    public ResponseEntity<PublisherDTO> getPublisherById(@PathVariable("id") Long id){

        PublisherDTO publisherDTO = publisherSevice.getPublisherById(id);

        return ResponseEntity.ok(publisherDTO);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> updatePublisher(@PathVariable("id") Long id,
                                                       @Valid @RequestBody PublisherRequest publisherRequest){

        publisherSevice.updatePublisher(id, publisherRequest);

        LibResponse response = new LibResponse(ResponseMessage.PUBLISHER_UPDATE_RESPONSE_MESSAGE, true);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> deletePublisher(@PathVariable("id") Long id){

        publisherSevice.deletePublisher(id);
        LibResponse response = new LibResponse(ResponseMessage.PUBLISHER_DELETE_RESPONSE_MESSAGE, true);
        return ResponseEntity.ok(response);
    }


}
