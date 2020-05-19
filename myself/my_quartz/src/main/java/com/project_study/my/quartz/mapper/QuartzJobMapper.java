package com.project_study.my.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project_study.my.quartz.entity.SysQuartzJobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName QuartzJobMapper
 * @Description: TODO
 * @Author zyl
 * @Date 2019/12/27
 * @Version V1.0
 **/
@Mapper
public interface QuartzJobMapper  extends BaseMapper<SysQuartzJobEntity> {
   /*
   * @MethodName: selectOneData
    * @Description: jobName 和  jobGroup 查询
    * @Param: [sysQuartzJobEntity]
    * @Return: com.project_study.quartz.entity.SysQuartzJobEntity
    * @Author: zyl
    * @Date: 2019/12/27
   **/
    SysQuartzJobEntity selectOneData(@Param("param") SysQuartzJobEntity sysQuartzJobEntity);


    /*
    * @MethodName: updateJobStatus
     * @Description: 按照 id 修改 jobStatus
     * @Param: [quartzEntity]
     * @Return: void
     * @Author: zyl
     * @Date: 2019/12/27
    **/
    void updateJobStatus(@Param("param") SysQuartzJobEntity quartzEntity);

    /*
    * @MethodName: selectList
     * @Description: 列表查询
     * @Param: [sysQuartzJobEntity]
     * @Return: java.util.List<com.project_study.quartz.entity.SysQuartzJobEntity>
     * @Author: zyl
     * @Date: 2019/12/27
    **/
    List<SysQuartzJobEntity> selectList(@Param("param") SysQuartzJobEntity sysQuartzJobEntity);



    void delete (@Param("id") String id);


     Integer update (@Param("param") SysQuartzJobEntity sysQuartzJobEntity);
}
