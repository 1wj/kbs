package com.digiwin.app.kbs.service.mws.dao.mysql.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName DemoMapper
 * @Description demo,一期忽略
 * @Version 1.0
 **/
@Mapper
public interface DemoMapper {
    /**
     * 更新 问题处理追踪
     * @author HeX
     * @date 2021/11/11
     **/
    int updateActionTrace();

}
