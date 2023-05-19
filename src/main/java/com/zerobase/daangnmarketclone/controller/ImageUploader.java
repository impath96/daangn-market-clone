package com.zerobase.daangnmarketclone.controller;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    String upload(MultipartFile multipartFile);

}
