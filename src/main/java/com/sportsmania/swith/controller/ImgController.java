package com.sportsmania.swith.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;

@RestController
@Log4j2
public class ImgController {


    @GetMapping("/img/{image_main}")
    public ResponseEntity<Resource> imgGET(@PathVariable String image_main) throws MalformedURLException {
        String uploadPath = "C:\\upload\\";

        File file = new File(uploadPath + image_main);
        if (file.exists()) {
            Resource resource = new UrlResource(file.toURI());
            HttpHeaders headers = new HttpHeaders();

            try {
                headers.add("Content-Type", Files.probeContentType(file.toPath()));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
            return ResponseEntity.ok().headers(headers).body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
