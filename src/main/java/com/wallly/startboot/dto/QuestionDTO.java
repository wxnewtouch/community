package com.wallly.startboot.dto;

import com.wallly.startboot.Model.User;
import lombok.Data;

@Data
public class QuestionDTO {
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
    private User user;
}
