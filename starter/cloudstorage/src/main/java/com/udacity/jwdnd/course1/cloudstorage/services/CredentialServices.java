package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialServices {

    private final CredentialMapper credentialMapper;
    private final HashService hashService;
    private final EncryptionService encryptionService;

    public CredentialServices(CredentialMapper credentialMapper, HashService hashService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    public boolean isCredentialNameAvailable(String username) {
        return credentialMapper.getCredential(username) == null;
    }

    public int createCredential(Credential credential) {
        credential(credential);
        return credentialMapper.insert(credential);
    }

    public int updateCredential(Credential credential) {
        credential(credential);
        return credentialMapper.updateCredential(credential);
    }

    private void credential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);
        credential.setKey(encodedSalt);
        credential.setPassword(hashedPassword);
    }

    public int deleteCredential(Integer credentialId) {
        return credentialMapper.delete(credentialId);
    }

    public List<Credential> getCredentials(Integer userId) {
        List<Credential> credentials =credentialMapper.getCredentials(userId);
        List<Credential> response = new ArrayList<>();
        credentials.forEach(c -> {
            c.setPasswordDecrypted(encryptionService.decryptValue(c.getPassword(), c.getKey()));
            response.add(c);
        });
        return response;
    }
}
