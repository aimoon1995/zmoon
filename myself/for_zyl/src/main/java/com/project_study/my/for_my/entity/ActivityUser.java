package com.project_study.my.for_my.entity;

import java.util.Date;

/**
 * @ClassName ActivityUser
 * @Description: TODO
 * @Author zyl
 * @Date 2019/12/25
 * @Version V1.0
 **/
public class ActivityUser  {
    /**
     * 主键
     */
    private String uuid;

    /**
     * 活动id
     */
    private String activityId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 已参与活动次数
     */
    private Integer joinCount;

    /**
     * 已成功分享活动次数
     */
    private Integer shareCount;

    /**
     * 分享人
     */
    private String shareUser;

    /**
     * 创建时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 剩余可用次数
     */
    private Integer remainCount;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Integer remainCount) {
        this.remainCount = remainCount;
    }
}
