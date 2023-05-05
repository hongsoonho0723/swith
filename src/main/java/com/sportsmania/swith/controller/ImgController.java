package com.sportsmania.swith.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;

@RestController
@Log4j2
public class ImgController {


    @GetMapping("/img/{image_main}")
    public ResponseEntity<Resource> imgGET(@PathVariable String image_main){
        String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\assets\\storyfile\\";

        Resource resource = new FileSystemResource(uploadPath+ File.separator + image_main);
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType( resource.getFile().toPath() ));
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
