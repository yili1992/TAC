package com.lee.tac.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * program: tac-root
 * description:
 * author: zhaoli@leoao.com
 * create: 2018-04-11 14:53
 **/
public class TestcaseDto implements Serializable {
    private static final long serialVersionUID = 1559479830696196273L;
    private Integer id;

    private String name;

    private String projectName;

    private String createTime;

    private String lastExcuteTime;

    private String author;

    private String content;

    private String startTime;

    private String endTime;

    //num: >0 标识正在执行数，0标识 未执行

    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastExcuteTime() {
        return lastExcuteTime;
    }

    public void setLastExcuteTime(String lastExcuteTime) {
        this.lastExcuteTime = lastExcuteTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
