package com.wallly.startboot.enums;

public enum  CommentTypeEnum {
    Question(1),
    Comment(2);
    
    private Integer type;
    CommentTypeEnum(Integer type){
        this.type = type;
    }

    /**
     * this method is not understand
     * @param type
     * @return
     */
    public static boolean isExits(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()){
            if (commentTypeEnum.getType() == type ){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
