package com.udacity.jwdnd.course1.cloudstorage.Controler;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialServices;
import com.udacity.jwdnd.course1.cloudstorage.services.FileServices;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteServices;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileServices fileServices;

    private final UserService userService;

    private final NoteServices noteServices;

    private final CredentialServices credentialServices;

    public HomeController(FileServices fileServices, UserService userService, NoteServices noteServices, CredentialServices credentialServices) {
        this.fileServices = fileServices;
        this.userService = userService;
        this.noteServices = noteServices;
        this.credentialServices = credentialServices;
    }

    @GetMapping()
    public String loginView(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        List<File> list =  fileServices.getFiles(user.getUserId());
        model.addAttribute("files",list);
        List<Note> notes = noteServices.getNotes(user.getUserId());
        model.addAttribute("notes",notes);
        List<Credential> credentials = credentialServices.getCredentials(user.getUserId());
        model.addAttribute("credentials", credentials);
        return "home";
    }
}
