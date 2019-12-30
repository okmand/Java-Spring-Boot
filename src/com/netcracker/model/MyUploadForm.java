package com.netcracker.model;

import org.springframework.web.multipart.MultipartFile;

public class MyUploadForm {

    // Upload files.
    private MultipartFile fileData;

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }
}