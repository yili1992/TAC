package com.lee.tac.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-18 14:04
 **/
public class TestcaseLogDto implements Serializable {


    private static final long serialVersionUID = -8654451844093404129L;
    private Integer id;

    private String testcaseName;

    private String startTime;

    private String status;

    private String endTime;

    private String logLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestcaseName() {
        return testcaseName;
    }

    public void setTestcaseName(String reportName) {
        this.testcaseName = reportName;
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
        this.logLink = logLink == null ? null : logLink.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
