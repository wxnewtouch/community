package com.wallly.startboot.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    /**
     * 他们的默认值都是false
     */
    private Boolean showFirstPage;
    private Boolean showPrevious;
    private Boolean showNext;
    private Boolean showEndPage;

    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;
    private Integer totalCount;

    public void setpagination(Integer size, Integer page) {
        this.page = page;
        /**
         * 在集合中添加页码
         */
        pages.add(page);
        for (int i = 1; i <= 3 ; i++){
            if (page - i > 0 ){
                pages.add(0,page - i);
            }
            if (page + i <= totalPage){
                pages.add(page + i);
            }
        }
        /**
         * 如果当前页为1的话，不显示
         * 前一页的按钮
         */
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        /**
         * 如果当前页为最后一页的话，
         * 不显示下一页的按钮
         */
        if (page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        /**
         * 展示第一页
         */
        if (pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }

        /**
         * 展示最后一页
         */
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }

    }
}
