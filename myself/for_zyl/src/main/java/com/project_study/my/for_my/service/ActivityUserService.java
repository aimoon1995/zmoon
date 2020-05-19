package com.project_study.my.for_my.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project_study.my.common.ResultBean;
import com.project_study.my.common.exception.BusinessException;
import com.project_study.my.common.param.BaseParam;
import com.project_study.my.for_my.entity.ActivityUser;
import com.project_study.my.for_my.mapper.ActivityUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @ClassName ActivityUserService
 * @Description: TODO
 * @Author zyl
 * @Date 2019/12/25
 * @Version V1.0
 **/
@Service
@Transactional
@Slf4j
public class ActivityUserService {

    @Resource
    private ActivityUserMapper activityUserMapper;

    public List<ActivityUser> queryUserData() {
        return activityUserMapper.selectList(null);
    }

    public  ActivityUser  selectByUuid() throws ConstraintViolationException {
        log.debug("======_++_+_++_+_+"+activityUserMapper.getClass().getMethods().toString());
        return activityUserMapper.selectByUuid();
    }


    public ResultBean queryUserByPage(BaseParam baseParam) {
        Page<ActivityUser> page = new Page<ActivityUser>(baseParam.getPageNum(), baseParam.getPageSize());
        page =  activityUserMapper.queryUserByPage(page);
        ResultBean<Object> bean = new ResultBean<Object>();
        bean.setData(page.getRecords());
        bean.setRecord((int) page.getTotal());
        bean.setPage((int) page.getCurrent());
        bean.setTotal((int) page.getPages());
        if (bean != null) {
            throw new BusinessException("");
        }
        return bean;
    }
}
