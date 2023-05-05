package com.sportsmania.swith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryFileDTO {

    //private String filename;

    private MultipartFile filename;

    private MultipartFile originalFileName;

    private Long story_no;

}
