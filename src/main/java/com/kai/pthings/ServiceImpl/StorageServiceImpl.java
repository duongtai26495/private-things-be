package com.kai.pthings.ServiceImpl;

import com.kai.pthings.config.Snippets;
import com.kai.pthings.entity.User;
import com.kai.pthings.service.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {
    private final Path storageFolder = Paths.get("uploads/images/");
    private final Path storageFolderProfile = Paths.get("uploads/profile");
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ImageServiceImpl imageService;

    public StorageServiceImpl() {
        try {
            Files.createDirectories(storageFolder);
            Files.createDirectories(storageFolderProfile);
        }catch (Exception e){
            throw new RuntimeException(Snippets.CANNOT_INITIALIZE_STORAGE,e);
        }
    }
    private boolean isImageFile (MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList(new String[] {"png","jpg","jpeg","bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file, String username) {
        int CHECK_UPLOAD = 0;

        if(username.equals(Snippets.NONAME)){
            CHECK_UPLOAD = 1;
        }
        else {
            CHECK_UPLOAD = 2;
        }

        try {
            if (file.isEmpty()) {
                throw new RuntimeException(Snippets.FAILED_STORE_EMPTY_FILE);
            }
            if (!isImageFile(file)) {
                throw new RuntimeException(Snippets.YOU_CAN_ONLY_UPLOAD_IMAGE);
            }
            float fileSize = file.getSize() / 1_000_000.0f;
            if (fileSize > 5.0f) {
                throw new RuntimeException(Snippets.SIZE_UPLOAD_FILE);
            }
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;


            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                throw new RuntimeException(Snippets.CANNOT_STORE_OUSIDE);
            }

            if(CHECK_UPLOAD == 2){
                destinationFilePath = this.storageFolderProfile.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
                if (!destinationFilePath.getParent().equals(this.storageFolderProfile.toAbsolutePath())){
                    throw new RuntimeException(Snippets.CANNOT_STORE_OUSIDE);
                }
            }

            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            if (CHECK_UPLOAD == 1){
                imageService.saveImageWithName(generatedFileName);
                return generatedFileName;

            }
            else {
                User user = new User();
                user.setUname(SecurityContextHolder.getContext().getAuthentication().getName());
                user.setProfile_image(generatedFileName);
                userService.changeProfileImage(user);

                return generatedFileName;
            }


        }catch (IOException e){
            throw new RuntimeException(Snippets.STORE_FILE_FAILED,e);
        }


    }


    @Override
    public Stream<Path> loadAllImage() {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> readFile(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(bytes);
            }else{
                return ResponseEntity.noContent().build();
            }
        }catch (IOException e){
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<byte[]> readProfileImage(String fileName) {
        try {
            Path file = storageFolderProfile.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(bytes);
            }else{
                return ResponseEntity.noContent().build();
            }
        }catch (IOException e){
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<byte[]> readProfileImageByUsername(String username) {
        if(username != null) {
            try {
                String fileName = userService.findByUsername(username).getProfile_image();
                Path file = storageFolderProfile.resolve(fileName);
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                    return ResponseEntity
                            .ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(bytes);
                } else {
                    return ResponseEntity.noContent().build();
                }
            } catch (IOException e) {
                return ResponseEntity.noContent().build();
            }
        }
        return null;
    }

    @Override
    public void deleteFile() {

    }
}
