package com.udacity.jwdnd.course1.cloudstorage.Controler;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileServices;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteServices;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller()
@RequestMapping("/note")
public class NoteController {

    private final NoteServices noteServices;

    private final UserService userService;

    public NoteController(NoteServices noteServices, UserService userService) {
        this.noteServices = noteServices;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String uploadFile(@ModelAttribute Note note, Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        note.setUserId(user.getUserId());
        if (!noteServices.isNoteTitleAvailable(note.getNoteTitle())) {
            return "/home";
        }
        int result = 0;
        if(note.getNoteId() == null)
            result = noteServices.createNote(note);
        else
            result = noteServices.updateNote(note);

        if (result == 1){
            model.addAttribute("successResult", true);
        }else{
            model.addAttribute("errorResult", true);
            model.addAttribute("errorResultMessage", "Error Uploading file");
        }
        return "result";
    }

    @GetMapping("/show")
    public String showFile(@RequestParam("fileID") int fileID, Model model) {
        Note note = noteServices.getNote(fileID);
        model.addAttribute("note",note);
        return "home";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("noteid") int fileID, Model model, HttpServletRequest request) {
        int result = 0;
        result = noteServices.deleteNote(fileID);
        if (result == 1) {
            model.addAttribute("successResult", true);
        } else {
            model.addAttribute("errorResult", true);
            model.addAttribute("errorResultMessage", "Error Deleting file");
        }
        return "result";
    }
}
