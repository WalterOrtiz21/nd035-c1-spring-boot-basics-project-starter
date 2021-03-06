package com.udacity.jwdnd.course1.cloudstorage.Controler;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileServices;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Controller()
@RequestMapping("/file")
public class FileController {

    private final FileServices fileServices;

    private final UserService userService;

    public FileController(FileServices fileServices, UserService userService) {
        this.fileServices = fileServices;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartFile fileUpload, Model model, HttpServletRequest request) throws IOException {
        File file = new File();
        String fileError = null;

        if (!fileServices.isFileNameAvailable(fileUpload.getOriginalFilename())) {
            fileError = "The fileName already exists.";
        }
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        file.setContentType(fileUpload.getContentType());
        file.setFileData(fileUpload.getBytes());
        file.setFileName(fileUpload.getOriginalFilename());
        file.setFileSize(fileUpload.getSize());
        file.setUserId(user.getUserId());
        int result = 0;
        if (fileError == null) {
            result = fileServices.createFile(file);
            if (result < 0) {
                fileError = "There was an error uploading the file. Please try again.";
            }
        }
        if (result == 1){
            model.addAttribute("successResult", true);
        }else{
            model.addAttribute("errorResult", true);
            model.addAttribute("errorResultMessage", fileError);
        }
        return "result";
    }

    @GetMapping("/show")
    public void showFile(@RequestParam("fileID") int fileID, HttpServletResponse response) throws IOException {
        File file = fileServices.getFile(fileID);
        response.setContentType(file.getContentType());
        response.setHeader("Content-Disposition", "inline; filename=\""+file.getFileName());
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "private");
        response.setHeader("Pragma", "no-store");
        response.getOutputStream().write(file.getFileData());
        response.flushBuffer();
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("fileID") int fileID, Model model) {
        int result = 0;
        result = fileServices.deleteFile(fileID);
        if (result == 1){
            model.addAttribute("successResult", true);
        }else{
            model.addAttribute("errorResult", true);
            model.addAttribute("errorResultMessage", "Delete file failed");
        }
        return "result";
    }
}
