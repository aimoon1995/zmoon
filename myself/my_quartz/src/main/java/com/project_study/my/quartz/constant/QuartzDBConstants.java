package com.project_study.my.quartz.constant;

public interface QuartzDBConstants {

    // 创建任务
    String SQLSYSQUARTZJOB_INSERT = "SysQuartzJob.insert";

    // 修改任务
    String SQLSYSQUARTZJOB_UPDATE = "SysQuartzJob.update";

    // 修改任务状态为启用状态
    String SQLSYSQUARTZJOB_UPDATESTARTSTATUS = "SysQuartzJob.updateStartStatus";

    // 修改任务状态为停用状态
    String SQLSYSQUARTZJOB_UPDATDISABLESTATUS = "SysQuartzJob.updateDisableStatus";

    // 根据jobName和jobGroup 查询任务
    String SQLSYSQUARTZJOB_SELECT = "SysQuartzJob.select";

    // 任务列表
    String SQLSYSQUARTZJOB_LIST = "SysQuartzJob.selectList";

    // 删除任务
    String SQLSYSQUARTZJOB_DETELE = "SysQuartzJob.delete";

}
