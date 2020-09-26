package com.udacity.jwdnd.course1.cloudstorage.Model;

import org.springframework.web.multipart.MultipartFile;

public class File {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private Integer userId;
    private byte[] fileData;

    public File(Integer fileId, String filename, String contenttype, Long filesize, Integer userid, byte[] filedata) {
        this.fileId = fileId;
        this.fileName = filename;
        this.contentType = contenttype;
        this.fileSize = filesize;
        this.userId = userid;
        this.fileData = filedata;
    }

    public File() {

    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("File{");
        sb.append("fileId=").append(fileId);
        sb.append(", filename='").append(fileName).append('\'');
        sb.append(", contenttype='").append(contentType).append('\'');
        sb.append(", filesize='").append(fileSize).append('\'');
        sb.append(", userid=").append(userId);
        sb.append(", filedata=").append(fileData);
        sb.append('}');
        return sb.toString();
    }
}


