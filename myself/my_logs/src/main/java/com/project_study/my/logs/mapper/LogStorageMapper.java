package com.project_study.my.logs.mapper;

import com.project_study.my.logs.entity.SystemLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName LogStorageMapper
 * @Description: TODO
 * @Author zyl
 * @Date 2020/1/2
 * @Version V1.0
 **/
@Mapper
public interface LogStorageMapper {

    void insertSystemLog(@Param("param") SystemLogEntity entity);
}
