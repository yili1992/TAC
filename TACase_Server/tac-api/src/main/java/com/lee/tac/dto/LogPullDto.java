package com.lee.tac.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-18 13:58
 **/
public class LogPullDto implements Serializable {

    private static final long serialVersionUID = 6722901291307044949L;
    private Integer id;

    private Integer TestcaseId;

    private String statusCode;

    private String startTime;

    private String endTime;

    private String logLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestcaseId() {
        return TestcaseId;
    }

    public void setTestcaseId(Integer testcaseId) {
        this.TestcaseId = testcaseId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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

    public String getLogLink() {
        return logLink;
    }

    public void setLogLink(String logLink) {
        this.logLink = logLink;
    }
}
