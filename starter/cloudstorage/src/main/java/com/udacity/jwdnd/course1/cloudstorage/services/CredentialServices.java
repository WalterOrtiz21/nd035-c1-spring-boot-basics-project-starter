package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialServices {

    private final CredentialMapper credentialMapper;
    private final HashService hashService;

    public CredentialServices(CredentialMapper credentialMapper, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
    }

    public boolean isCredentialNameAvailable(String username) {
        return credentialMapper.getCredential(username) == null;
    }

    public int createCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(credential.getPassword(), encodedSalt);
        credential.setKey(credential.getPassword());
        credential.setPassword(hashedPassword);
        return credentialMapper.insert(credential);
    }

    public int updateCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(credential.getPassword(), encodedSalt);
        credential.setKey(credential.getPassword());
        credential.setPassword(hashedPassword);
        return credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }

    public List<Credential> getCredentials(Integer userId) {

        return credentialMapper.getCredentials(userId);
    }
}
