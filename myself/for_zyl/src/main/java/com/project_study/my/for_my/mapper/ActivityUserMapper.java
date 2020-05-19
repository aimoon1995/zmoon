package com.project_study.my.for_my.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project_study.my.for_my.entity.ActivityUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ActivityUserMapper
 * @Description: TODO
 * @Author zyl
 * @Date 2019/12/25
 * @Version V1.0
 **/
@Mapper
public interface ActivityUserMapper extends BaseMapper<ActivityUser> {

    ActivityUser selectByUuid();

    Page<ActivityUser> queryUserByPage(Page<ActivityUser> page);
}
