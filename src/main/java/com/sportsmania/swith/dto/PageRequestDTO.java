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
    @Builder.Default
    @Min(value = 1)
    @Positive
    private int page =1;

    @Builder.Default
    @Min(value = 10)
    @Max(value = 100)
    @Positive
    private int size = 10;
    private String link;

    public int getSkip(){ return (page-1)*10;}

    private String[] types; //검색종류 제목t,내용c,작성자w
    /*public String[] getTypes(){
        if(types == null || types.isEmpty()){
            return null;
        }
        return types.split("");
    }*/
    private String b_category; //서포터구인,체육활동지원,운동친구모집
    private String keyword;
    private boolean finished;
    private String startdate;
    private String enddate;
    private String supporttype;
    private String region;
    private String region2;
    public String getLink(){
        if(link==null){
            StringBuilder builder = new StringBuilder();
            builder.append("page="+this.page);
            builder.append("&size="+this.size);
            link = builder.toString();
        }
        return link;
    }

}
