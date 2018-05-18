package com.lee.tac.dto;

import java.io.Serializable;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-11 16:11
 **/
public class ProjectDto implements Serializable {

    private static final long serialVersionUID = -9007328679197659314L;
    private Integer id;

    private String name;

    private String startTime;

    private String endTime;

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
}
