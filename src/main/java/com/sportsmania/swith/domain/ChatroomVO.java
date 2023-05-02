package com.sportsmania.swith.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatroomVO {
    private int board_no;
    private String board_writer;
    private String room_title;
    private LocalDateTime room_createdAt;
    private LocalDateTime room_inAt;
}
