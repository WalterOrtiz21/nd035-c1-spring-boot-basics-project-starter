package com.udacity.jwdnd.course1.cloudstorage.Model;

public class Credential {

    private Integer credentialId;
    private String url;
    private String userName;
    private String key;
    private String password;
    private String passwordDecrypted;
    private Integer userId;

    public Credential(Integer credentialId, String url, String userName, String key, String password, String passwordEncrypted, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;
        this.passwordDecrypted = passwordEncrypted;
        this.userId = userId;
    }

    public Credential() {
    }


    public String getPasswordDecrypted() {
        return passwordDecrypted;
    }

    public void setPasswordDecrypted(String passwordDecrypted) {
        this.passwordDecrypted = passwordDecrypted;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Credential{");
        sb.append("credentialId=").append(credentialId);
        sb.append(", url='").append(url).append('\'');
        sb.append(", username='").append(userName).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", userid=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}


