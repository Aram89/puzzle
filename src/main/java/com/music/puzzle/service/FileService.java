package com.music.puzzle.service;

import com.music.puzzle.exception.AppException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by Aram on 10/21/18.
 */
public interface FileService {

    void storeFile(String path, MultipartFile file) throws AppException;
    Resource loadFile(String fileName) throws AppException;
}
