package com.lee.tac.job;

import com.lee.tac.controller.TestCaseController;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public class ExecuteJob implements BaseJob {
  
    private static Logger logger = LoggerFactory.getLogger(ExecuteJob.class);
    private static ApplicationContext applicationContext;
    private static TestCaseController testCaseController;
    public ExecuteJob() {
          
    }

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
        testCaseController = (TestCaseController)applicationContext.getBean(TestCaseController.class);
    }


    @Override
    public void execute(JobExecutionContext context)  
        throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String testcaseId =  (String)dataMap.get("testcaseId");
        testCaseController.executeCase(testcaseId);
        logger.info("定时任务执行testcase: "+testcaseId);
          
    }
}  
