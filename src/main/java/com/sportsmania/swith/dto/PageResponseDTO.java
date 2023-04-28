package com.sportsmania.swith.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PageResponseDTO<E> {
    private int page;
    private int size;
    private int total;

    //시작페이지 번호
    private int start;
    //끝페이지 번호
    private int end;

    //이전페이지의 존재 여부
    private boolean prev;
    //다음페이지의 존재 여부
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO,
                           List<E> dtoList, int total){
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total=total;
        this.dtoList=dtoList;

        this.end = (int)(Math.ceil(this.page/10.0))*10;

        this.start = this.end - 9;
        int last = (int)(Math.ceil((total/(double)size)));

        this.end = end > last ? last : end;
//        이전/다음페이지 계산
        this.prev = this.start > 1;

        this.next = total > this.end * this.size;
    }
}
