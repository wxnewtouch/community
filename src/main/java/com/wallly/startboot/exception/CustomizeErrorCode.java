package com.wallly.startboot.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不存在了，要不要换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何回复或者评论"),
    NO_LOGIN(2003,"请先登陆后在重试"),
    SYSTEM_ERROR(2004,"服务器端冒烟了，请稍后再试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006,"你回复的评论不存在了" ),
    COMMENT_IS_EMPTY(2007,"评论内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"你读的是别人的信息啊"),
    NOTIFICATION_NOT_FOUND(2009,"不翼而飞了！")
    ;


    private String message;
    private Integer code;


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
