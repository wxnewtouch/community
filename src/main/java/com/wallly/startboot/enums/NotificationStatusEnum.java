package com.wallly.startboot.enums;

public enum NotificationStatusEnum {
    READ(1),UNREAD(0);
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }
}
