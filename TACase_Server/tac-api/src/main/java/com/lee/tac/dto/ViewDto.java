package com.lee.tac.dto;

import java.io.Serializable;

/**
 * author zhaoli
 */
public class ViewDto implements Serializable {

    private static final long serialVersionUID = -8089196707948871390L;

    private Integer id;

    private String startTime;

    private String endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
