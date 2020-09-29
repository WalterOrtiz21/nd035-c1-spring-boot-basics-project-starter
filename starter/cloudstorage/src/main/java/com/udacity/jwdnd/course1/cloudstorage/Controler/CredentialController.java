package com.udacity.jwdnd.course1.cloudstorage.Controler;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialServices;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
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
        int result = 0;
        if(credential.getCredentialId() == null){
            result = credentialServices.createCredential(credential);
        }else{
            result = credentialServices.updateCredential(credential);
        }
        if (result == 1){
            model.addAttribute("successResult", true);
        }else{
            model.addAttribute("errorResult", true);
            model.addAttribute("errorResultMessage", "Process Failed");
        }
        return "result";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("credentialid") int credentialId, Model model, HttpServletRequest request) {
        int result = 0;
        result = credentialServices.deleteCredential(credentialId);
        if (result == 1){
            model.addAttribute("successResult", true);
        }else{
            model.addAttribute("errorResult", true);
            model.addAttribute("errorResultMessage", "Process Failed");
        }
        return "result";
    }
}
