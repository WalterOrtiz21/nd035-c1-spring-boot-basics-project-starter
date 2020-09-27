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
        if (!noteServices.isNoteTitleAvailable(note.getNoteTitle())) {
            List<Note> notes = noteServices.getNotes(user.getUserId());
            model.addAttribute("notes",notes);
            model.addAttribute("isNote",true);
            return "home";
        }
        if(note.getNoteId() == null){
            note.setUserId(user.getUserId());
            noteServices.createNote(note);
        }else{
            noteServices.updateNote(note);
        }
        List<Note> notes = noteServices.getNotes(user.getUserId());
        model.addAttribute("notes",notes);
        return "home";
    }

    @GetMapping("/show")
    public String showFile(@RequestParam("fileID") int fileID, Model model) {
        Note note = noteServices.getNote(fileID);
        model.addAttribute("note",note);
        return "home";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("noteid") int fileID, Model model, HttpServletRequest request) {
            noteServices.deleteNote(fileID);
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        List<Note> list =  noteServices.getNotes(user.getUserId());
        model.addAttribute("notes",list);
        return "home";
    }
}
