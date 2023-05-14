package com.sportsmania.swith.domain;

import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatroomsVO {
    private int room_no;
    private String userId;
    private String roomTitle;
}
