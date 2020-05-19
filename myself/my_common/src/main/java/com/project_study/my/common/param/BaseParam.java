package com.project_study.my.common.param;

import javax.validation.constraints.Min;
import  com.project_study.my.common.group.*;

/**
 * @ClassName BaseParam
 * @Description: TODO
 * @Author zyl
 * @Date 2019/12/30
 * @Version V1.0
 **/
public class BaseParam {

    @Min(message = "不能为空", value = 11,groups = {AddGroup.class})
    private  Integer pageNum;

    private  Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
