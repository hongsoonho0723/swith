package com.sportsmania.swith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    private String[] types; //검색종류
    private String keyword; //검색 종류에 따라 사용할 키워드

    private LocalDate from; //구간 시작값
    private LocalDate to; //구간 중료값

    //페이지 번호
    @Builder.Default
    @Min(value = 1)
    @Positive
    private int page = 1;

    //한 페이지당 개수
    @Builder.Default
    @Min(value = 10)
    @Max(value = 100)
    @Positive
    private int size = 10;

    //limit에서 사용하는 건너뛰기의 수
    public int getSkip(){
        return (page -1) * 10;
    }

    //페이지 이동정보를 담는 문자열
    private String link;

    //GET방식으로 페이지 이동에 필요한 링크 생성
    public String getLink() {
        if(link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);
            link = builder.toString();
        }
        return link;
    }
}
