package com.sportsmania.swith.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private  Long reply_no;

    @NotNull
    private Long story_no;


    private String reply_writer;

    @NotEmpty
    private String content;

    private String regdate;

    private String image_profile;

    private String nickname;
}
