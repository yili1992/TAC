package com.lee.tac.controller;

import com.lee.tac.inner.CronTaskService;
import com.lee.tac.job.ExecuteJob;
import com.lee.tac.mapper.CronTaskMapper;
import com.lee.tac.model.CronTask;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="/scheduler")
public class JobController 
{
	private static String JOB_GROUP = "JOB_TAC";
	private static Logger logger = LoggerFactory.getLogger(JobController.class);


	@Autowired
    @Qualifier("Scheduler")
	private Scheduler scheduler;

	@Autowired
    CronTaskService cronTaskService;

    @Autowired
    CronTaskMapper cronTaskMapper;

	@CrossOrigin
	@PostMapping(value="/addjob")
	public ResponseEntity<Map<String,Object>> addJob(@RequestBody Map<String,Object> reqMap) throws Exception
	{
        String testCase = reqMap.get("testCase").toString();
        String jobAuthor = reqMap.get("jobAuthor").toString();
        String cronExpression = reqMap.get("cronExpression").toString();
        Map<String,Object> map = new HashMap<String,Object>();
	    String result = cronTaskService.saveCronTask(Integer.valueOf(testCase),jobAuthor, cronExpression);
	    try {
            if ("00000".equals(result)){
                addJobHandler(testCase, JOB_GROUP, cronExpression);
                map.put("code","00000");
                map.put("msg","增加定时任务成功");
            } else {
                map.put("code",result);
                map.put("msg","增加定时任务失败");
            }
        } catch (Exception e){
            List<CronTask> cronTaskList = cronTaskMapper.selectExistTaskByTestcaseId(Integer.valueOf(testCase));
            cronTaskService.deleteCronTask(cronTaskList.get(0).getId());
            map.put("code","10003");
            map.put("msg",e.getMessage());
        }

        return new ResponseEntity<>(map, HttpStatus.OK);

	}
	
	public void addJobHandler(String jobName, String jobGroupName, String cronExpression)throws Exception{
        
        // 启动调度器  
		scheduler.start(); 

		//构建job信息
		JobDetail jobDetail = JobBuilder.newJob(ExecuteJob.class).withIdentity(jobName, jobGroupName).build();
		jobDetail.getJobDataMap().put("testcaseId", jobName);
		//表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
            .withSchedule(scheduleBuilder).build();
        
        try {
        	scheduler.scheduleJob(jobDetail, trigger);
            
        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败"+e);
            throw new Exception("创建定时任务失败");
        }
	}

	@CrossOrigin
	@PostMapping(value="/pausejob")
	public ResponseEntity<Map<String,Object>> pauseJob(@RequestBody Map<String,Object> reqMap) throws Exception
	{
        String jobName = reqMap.get("jobName").toString();
        String taskId = reqMap.get("taskId").toString();
		Map<String,Object> map = new HashMap<String,Object>();
		boolean result = cronTaskService.updateCronTask(Integer.valueOf(taskId),null,null, false,false);
		try {
			if (result){
				jobPause(jobName, JOB_GROUP);
				map.put("code","00000");
				map.put("msg","暂停定时任务成功");
			} else {
				map.put("code","10001");
				map.put("msg","暂停定时任务失败");
			}
		} catch (Exception e){
			result = cronTaskService.updateCronTask(Integer.valueOf(taskId),null,null, true,false);
			map.put("code","99999");
			map.put("msg",e.getMessage());
		}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	public void jobPause(String jobName, String jobGroupName) throws Exception
	{	
		scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
	}

	@CrossOrigin
	@PostMapping(value="/resumejob")
	public ResponseEntity<Map<String,Object>> resumeJob(@RequestBody Map<String,Object> reqMap) throws Exception
	{
        String jobName = reqMap.get("jobName").toString();
        String taskId = reqMap.get("taskId").toString();
        Map<String,Object> map = new HashMap<String,Object>();
        boolean result = cronTaskService.updateCronTask(Integer.valueOf(taskId),null,null, true,false);
        try {
            if (result){
                jobResume(jobName, JOB_GROUP);
                map.put("code","00000");
                map.put("msg","恢复定时任务成功");
            } else {
                map.put("code","10001");
                map.put("msg","恢复定时任务失败");
            }
        } catch (Exception e){
            result = cronTaskService.updateCronTask(Integer.valueOf(taskId),null,null, false,false);
            map.put("code","99999");
            map.put("msg",e.getMessage());
        }

        return new ResponseEntity<>(map, HttpStatus.OK);

	}
	
	public void jobResume(String jobName, String jobGroupName) throws Exception
	{
		scheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
	}

	@CrossOrigin
	@PostMapping(value="/reschedulejob")
	public ResponseEntity<Map<String,Object>> reScheduleJob(@RequestBody Map<String,Object> reqMap) throws Exception
	{
        String jobName = reqMap.get("jobName").toString();
        String jobAuthor = reqMap.get("jobAuthor").toString();
        String cronExpression = reqMap.get("cronExpression").toString();
        String taskId = reqMap.get("taskId").toString();
        Map<String,Object> map = new HashMap<String,Object>();
        boolean result = cronTaskService.updateCronTask(Integer.valueOf(taskId),jobAuthor,cronExpression, true,false);
        try {
            if (result){
                jobReschedule(jobName, JOB_GROUP, cronExpression);
                map.put("code","00000");
                map.put("msg","更新定时任务成功");
            } else {
                map.put("code","10001");
                map.put("msg","更新定时任务失败");
            }
        } catch (Exception e){
            result = cronTaskService.updateCronTask(Integer.valueOf(taskId),jobAuthor,cronExpression, true,false);
            map.put("code","99999");
            map.put("msg",e.getMessage());
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	public void jobReschedule(String jobName, String jobGroupName, String cronExpression) throws Exception
	{				
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            ((CronTriggerImpl)trigger).setStartTime(new Date());
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			System.out.println("更新定时任务失败"+e);
			throw new Exception("更新定时任务失败");
		}
	}

	@CrossOrigin
	@PostMapping(value="/deletejob")
	public ResponseEntity<Map<String,Object>> deleteJob(@RequestBody Map<String,Object> reqMap) throws Exception
	{
        String jobName = reqMap.get("jobName").toString();
        String taskId = reqMap.get("taskId").toString();
        Map<String,Object> map = new HashMap<String,Object>();
        boolean result = cronTaskService.deleteCronTask(Integer.valueOf(taskId));
        try {
            if (result){
                scheduler.pauseJob(JobKey.jobKey(jobName, JOB_GROUP));
                jobDelete(jobName, JOB_GROUP);
                map.put("code","00000");
                map.put("msg","删除定时任务成功");
            } else {
                map.put("code","10001");
                map.put("msg","删除定时任务失败");
            }
        } catch (Exception e){
            result = cronTaskService.updateCronTask(Integer.valueOf(taskId),null,null, false,false);
            map.put("code","99999");
            map.put("msg",e.getMessage());
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	public void jobDelete(String jobName, String jobGroupName) throws Exception
	{		
		scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobGroupName));
		scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobGroupName));
		scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
	}

}
