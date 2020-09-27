package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServices {

    private final FileMapper fileMapper;

    public FileServices(FileMapper fileMaper) {
        this.fileMapper = fileMaper;
    }

    public int createFile(File file) {
        System.out.println("Vamos a agregar el archivo");
        return fileMapper.insert(file);
    }

    public List<File> getFiles(Integer userId) {
        return fileMapper.getFiles(userId);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public File getFileByName(String fileName) {
        return fileMapper.getFileByName(fileName);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.delete(fileId);
    }

    public boolean isFileNameAvailable(String fileName) {
        return getFileByName(fileName) == null;
    }
}
