package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Filter;

/**
 * File upload and download
 * @author Diyang Li
 * @create 2022-07-09 11:33 PM
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    // This annotation can be used for injecting values into fields in Spring-managed beans, and it can be applied
    // at the field or constructor/method parameter level.
    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info(file.toString());
        // save the temperate file to a specific place

        // original filename
        String originalFilename = file.getOriginalFilename();
        // ie: .jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Using UUID( universally unique identifier) regenerate file name
        String fileName = UUID.randomUUID().toString() + suffix;

        // if the basepath exists, if not build a new file
        File dir = new File(basePath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }
}
