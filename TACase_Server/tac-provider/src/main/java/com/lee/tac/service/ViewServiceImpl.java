package com.lee.tac.service;

import com.lee.tac.inner.ViewService;
import com.lee.tac.model.View;
import com.lee.tac.utils.DateHelper;
import com.lee.tac.dto.ViewDto;
import com.lee.tac.mapper.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-11 16:17
 **/
@Service("viewService")
public class ViewServiceImpl implements ViewService {
    private final static Logger logger = LoggerFactory.getLogger(ViewServiceImpl.class);

    @Autowired
    private ViewMapper viewMapper;

    @Override
    public void addPageView(){
        ViewDto viewDto = new ViewDto();
        String startTime = DateHelper.getCurrentDate("yyyy-MM-dd");
        String endTime = DateHelper.getSpecifiedDayAfter(startTime,1,"yyyy-MM-dd");
        viewDto.setStartTime(startTime);
        viewDto.setEndTime(endTime);
        List<View> viewList = viewMapper.getViewCountList(viewDto);
        if(viewList.size()==0){
            View view = new View();
            view.setUv(0);
            view.setPv(1);
            view.setTime(DateHelper.getCurrentDate("yyyy-MM-dd"));
            viewMapper.insert(view);
        }
        if(viewList.size()==1){
            int currentPv = viewList.get(0).getPv();
            viewList.get(0).setPv(currentPv+1);
            viewMapper.updateByPrimaryKey(viewList.get(0));
        }
        if(viewList.size()>1) {
            logger.error("每日浏览记录日期发现重复:"+viewList.size());
        }
    }
}
