package com.lee.tac.model;

import java.util.Date;

public class Testcase {
    private Integer id;

    private String name;

    private Integer projectId;

    private String createTime;

    private String lastExcuteTime;

    private String author;

    private String content;

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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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