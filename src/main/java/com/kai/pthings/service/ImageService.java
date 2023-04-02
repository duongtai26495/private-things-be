package com.kai.pthings.service;

import com.kai.pthings.entity.Image;

import java.util.List;

public interface ImageService {
    String saveImageWithName(String name);

    List<Image> getAllImage();

    void deleteImageById(Long id);
}
