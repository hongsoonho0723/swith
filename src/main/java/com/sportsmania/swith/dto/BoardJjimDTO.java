package com.sportsmania.swith.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class BoardJjimDTO {
    private int wish_no;
    private String userid;
    private int board_no;
}
