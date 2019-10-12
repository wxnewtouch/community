package com.wallly.startboot.Model;

import lombok.Data;

@Data
public class Notification {
    private Integer id;
    //通知者 当前的评论人
    private Integer notifier;
    private String notifierName;
    //收到评论的人 应该是这个问题的创建人的id
    private Integer receiver;
    //外健 评论id
    private Integer outerId;
    private String outerTitle;
    private Integer type;
    private Long gmtCreate;
    private Integer status;

}
