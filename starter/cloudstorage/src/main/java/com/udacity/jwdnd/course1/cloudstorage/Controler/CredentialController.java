package com.udacity.jwdnd.course1.cloudstorage.Controler;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialServices;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteServices;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller()
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialServices credentialServices;

    private final UserService userService;

    public CredentialController(CredentialServices credentialServices, UserService userService) {
        this.credentialServices = credentialServices;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String uploadFile(@ModelAttribute Credential credential, Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        credential.setUserId(user.getUserId());
        if(credential.getCredentialId() == null){
            credentialServices.createCredential(credential);
        }else{
            credentialServices.updateCredential(credential);
        }

        List<Credential> credentials = credentialServices.getCredentials(user.getUserId());
        model.addAttribute("credentials",credentials);
        return "home";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("credentialid") int credentialId, Model model, HttpServletRequest request) {
        credentialServices.deleteCredential(credentialId);
        Principal principal = request.getUserPrincipal();
        User user = userService.getUser(principal.getName());
        List<Credential> credentials = credentialServices.getCredentials(user.getUserId());
        model.addAttribute("credentials",credentials);
        return "home";
    }
}
