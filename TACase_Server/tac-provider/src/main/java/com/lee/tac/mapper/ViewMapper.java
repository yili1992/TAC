package com.lee.tac.mapper;

import com.lee.tac.dto.ViewDto;
import com.lee.tac.model.View;

import java.util.List;

public interface ViewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(View record);

    int insertSelective(View record);

    View selectByPrimaryKey(Integer id);

    List<View> getViewCountList(ViewDto viewDto);

    int updateByPrimaryKeySelective(View record);

    int updateByPrimaryKey(View record);
}