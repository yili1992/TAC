package com.lee.tac.dto;

import java.io.Serializable;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-21 11:47
 *
 * author zhaoli*/
public class StatisticsDto implements Serializable {

    private static final long serialVersionUID = 2965919634353011790L;

    private Integer reportCount;

    private Integer reportLogCount;

    private Integer projectCount;

    private Integer viewCount;

    public Integer getReportCount() {
        return reportCount;
    }

    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }

    public Integer getReportLogCount() {
        return reportLogCount;
    }

    public void setReportLogCount(Integer reportLogCount) {
        this.reportLogCount = reportLogCount;
    }

    public Integer getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(Integer projectCount) {
        this.projectCount = projectCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
