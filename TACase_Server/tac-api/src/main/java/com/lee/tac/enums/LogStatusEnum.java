package com.lee.tac.enums;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-27 14:50
 **/
public enum LogStatusEnum {

    DONE("done", 1), DOING("doing", 2), NOTSTART("notstart", 4), TERMINATED("terminated", 5);


    private String name ;
    private int code ;

    LogStatusEnum(String name, int code){
        this.name = name ;
        this.code = code ;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }


}