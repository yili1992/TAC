package com.lee.tac.dto;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-21 15:12
 **/
public class StatisticsYearDto {
    private String month;

    private Integer count;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
