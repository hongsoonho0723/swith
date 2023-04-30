package com.sportsmania.swith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryReplyDTO {

    private Long story_no;

    @NotEmpty
    private String reply_writer;

    @NotEmpty
    private String content;

    private LocalDateTime regdate;
}
