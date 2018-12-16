package com.music.puzzle.service;

import com.music.puzzle.exception.AppException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    public void storeFile(String fileName, MultipartFile file) throws AppException {
        try {
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = Paths.get(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new AppException("Could not store file " + fileName + ". Please try again!");
        }
    }

    public Resource loadFile(String fileName) throws AppException {
        try {
            Path filePath = Paths.get(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new AppException("File not found " + filePath.toString());
            }
        } catch (MalformedURLException ex) {
            throw new AppException("File not found " + fileName);
        }
    }

}
