package com.wallly.startboot.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer parentId;
    private String content;
    private Integer type;
}
