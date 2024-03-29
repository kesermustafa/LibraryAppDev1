package com.lib.controller;

import com.lib.domain.ImageFile;
import com.lib.dto.ImageFileDTO;
import com.lib.dto.response.ImageSavedResponse;
import com.lib.dto.response.LibResponse;
import com.lib.dto.response.ResponseMessage;
import com.lib.service.ImageFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class ImageFileController {

    private final ImageFileService imageFileService;


    public ImageFileController(ImageFileService imageFileService) {
        this.imageFileService = imageFileService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageSavedResponse> uploadFile(@RequestParam("file") MultipartFile file){

        String imageId = imageFileService.saveImage(file);

        ImageSavedResponse response =
                new ImageSavedResponse(imageId, ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE, true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id){

        ImageFile imageFile = imageFileService.getImageById(id);

        return ResponseEntity.ok().header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + imageFile.getName()).
                body(imageFile.getImageData().getData());

    }


    // !! Image Display
    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]> displayFile(@PathVariable String id){

        ImageFile imageFile = imageFileService.getImageById(id);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageFile.getImageData().getData(), header, HttpStatus.OK);
    }


    // !! --- GetAll Images -----
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ImageFileDTO>> getAllImages(){

        List<ImageFileDTO> allImageDTO = imageFileService.getAllImages();

        return ResponseEntity.ok(allImageDTO);
    }



    // !! ---Delete Images -----
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LibResponse> deleteImageFile(@PathVariable String id){

        imageFileService.removeById(id);

        LibResponse response = new LibResponse(ResponseMessage.IMAGE_DELETED_RESPONSE_MESSAGE, true);

        return ResponseEntity.ok(response);
    }




}
