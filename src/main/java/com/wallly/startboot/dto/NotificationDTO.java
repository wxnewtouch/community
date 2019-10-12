package com.wallly.startboot.dto;

import com.wallly.startboot.Model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private String notifierName;
    private String outerTitle;
    private Integer outerid;
    private Integer type;
    private String typeName;
}
