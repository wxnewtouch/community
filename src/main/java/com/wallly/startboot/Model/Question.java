package com.wallly.startboot.Model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String tag;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private String creator;
}
