package com.kai.pthings.ServiceImpl;

import com.kai.pthings.config.Snippets;
import com.kai.pthings.entity.Image;
import com.kai.pthings.repository.ImageRepository;
import com.kai.pthings.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;


    @Override
    public List<Image> getAllImage() {
        return imageRepository.findAll();
    }

    @Override
    public void deleteImageById(Long id) {
        if(imageRepository.existsById(id)){
            imageRepository.deleteById(id);
        }
    }



    @Override
    public String saveImageWithName(String name) {
        if(name != null){
            Image image = new Image();

            image.setAdded_at(Snippets.getCurrentDateTime());
            image.setAdded_by(SecurityContextHolder.getContext().getAuthentication().getName());
            image.setName(name);
            imageRepository.save(image);
            return image.getName();
        }
        return null;
    }
}
