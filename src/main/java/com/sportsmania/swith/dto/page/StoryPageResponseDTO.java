package com.sportsmania.swith.dto.page;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
public class StoryPageResponseDTO<E>{

    private int page;
    private int size;
    private int total;

    //시작 페이지
    private int start;

    //끝 페이지
    private int end;

    //이전 페이지의 존재 여부
    private boolean prev;

    //다음 페이지의 존재 여부
    private boolean next;

    private List<E> dtoList;

    //생성자
    @Builder(builderMethodName = "withAll")
    public StoryPageResponseDTO(StoryPageRequestDTO storyPageRequestDTO, List<E> dtoList, int total){
        this.page = storyPageRequestDTO.getPage();
        this.size = storyPageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page / 10.0 )) * 10;
        this.start = this.end - 9;
        int last = (int)(Math.ceil((total/(double)size)));
        this.end = end > last ? last: end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }


}
