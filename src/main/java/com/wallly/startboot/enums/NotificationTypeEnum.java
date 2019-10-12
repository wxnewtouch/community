package com.wallly.startboot.enums;

public enum NotificationTypeEnum {
    REPLY_COMMENT(2, "回复了评论"),
    REPLY_QUESTION(1, "回复了问题");

    public static String nameOfType(Integer type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    private Integer type;
    private String name;

    NotificationTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
