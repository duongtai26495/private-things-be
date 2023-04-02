package com.kai.pthings.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    public String storeFile(MultipartFile file, String username);
    public Stream<Path> loadAllImage();
    public ResponseEntity<byte[]> readFile(String fileName);
    public ResponseEntity<byte[]> readProfileImage(String fileName);
    public ResponseEntity<byte[]> readProfileImageByUsername(String username);

    public void deleteFile();
}
