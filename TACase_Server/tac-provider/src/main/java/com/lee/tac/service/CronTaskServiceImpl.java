package com.lee.tac.service;

import com.lee.tac.inner.CronTaskService;
import com.lee.tac.mapper.CronTaskMapper;
import com.lee.tac.model.CronTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * program: TACase_Server
 * description: ${description}
 * author: zhaoli
 * create: 2018-06-02 15:55
 **/
@Service("taskService")
public class CronTaskServiceImpl implements CronTaskService {
    private final static Logger logger = LoggerFactory.getLogger(CronTaskServiceImpl.class);

    @Autowired
    CronTaskMapper cronTaskMapper;

    @Override
    public String saveCronTask(Integer testcaseId, String author, String cron) {
        try{
            if (cronTaskMapper.selectExistTaskByTestcaseId(testcaseId).size()>0){
                logger.info("该测试集已经存在定时任务");
                return  "10002";
            }
            CronTask cronTask = new CronTask();
            cronTask.setAuthor(author);
            cronTask.setCron(cron);
            cronTask.setTestcaseId(testcaseId);
            int cronTaskId = cronTaskMapper.insertSelective(cronTask);
            if (cronTaskId != 0){
                return  "00000";
            } else {
                logger.info("增加定时任务失败");
                return  "10001";
            }
        } catch (Exception e) {
            logger.error("add task error", e);
            return  "99999";
        }
    }

    @Override
    public boolean updateCronTask(Integer id, String author, String cron, boolean enable, boolean isDelete){
        try{
            CronTask cronTask = new CronTask();
            cronTask.setId(id);
            cronTask.setAuthor(author);
            cronTask.setCron(cron);
            cronTask.setEnable(enable);
            cronTask.setIsDelete(isDelete);
            int cronTaskId = cronTaskMapper.updateByPrimaryKeySelective(cronTask);
            if (cronTaskId == 1){
                logger.info("定时任务更新成功: "+id);
                return true;
            } else {
                logger.error("更新任务失败: "+id);
                return false;
            }

        } catch (Exception e) {
            logger.error(String.format("update %s task error: %s", id,e));
            return false;
        }
    }

    @Override
    public boolean deleteCronTask(Integer id){
        try{
            CronTask cronTask = cronTaskMapper.selectByPrimaryKey(id);
            cronTask.setIsDelete(Boolean.TRUE);
            int cronTaskId = cronTaskMapper.updateByPrimaryKeySelective(cronTask);
            if (cronTaskId == 1){
                logger.info("定时任务删除成功: "+id);
                return true;
            } else {
                logger.error("定时任务删除失败: "+id);
                return false;
            }
        } catch (Exception e) {
            logger.error(String.format("delete %s task error: %s", id,e));
            return false;
        }
    }

}
