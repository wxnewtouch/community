package com.wallly.startboot.Model;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
}
