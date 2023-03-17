package com.lib.service;

import com.lib.domain.ImageData;
import com.lib.domain.ImageFile;
import com.lib.dto.ImageFileDTO;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.repository.ImageFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;


    public ImageFileService(ImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }

    public ImageFile findImageById(String imageId) {

        return imageFileRepository.findImageById(imageId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE,imageId)));


    }

    public String saveImage(MultipartFile file) {
        ImageFile imageFile = null;

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Data (image kendisi)
        try {
            ImageData imageData = new ImageData(file.getBytes());
            imageFile = new ImageFile(fileName, file.getContentType(), imageData);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        imageFileRepository.save(imageFile);

        return imageFile.getId();
    }


    public ImageFile getImageById(String imageId) {
        return imageFileRepository.findById(imageId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE,imageId)));

    }



    public List<ImageFileDTO> getAllImages() {

        List<ImageFile> imageFiles = imageFileRepository.findAll();

        List<ImageFileDTO> imageFileDTOS = imageFiles.stream().map(imageFile ->{
            String imageUri = ServletUriComponentsBuilder.
                    fromCurrentContextPath(). // localhost:8080
                            path("/files/download/").    // localhost:8080/files/download/
                            path(imageFile.getId()).toUriString(); // localhost:8080/files/download/id

            return new ImageFileDTO(imageFile.getName(), imageUri, imageFile.getType(), imageFile.getLength());
        } ).collect(Collectors.toList());

        return imageFileDTOS;

    }


    public void removeById(String id) {
        ImageFile imageFile = getImageById(id);

        imageFileRepository.delete(imageFile);
    }




}
