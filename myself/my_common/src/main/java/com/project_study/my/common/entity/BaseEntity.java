package com.project_study.my.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseEntity implements Serializable, Cloneable {

	@ApiModelProperty(value = "物理主键ID,新增时后端生成")
	private String id;

	// 创建日期;
    @ApiModelProperty(value = "创建日期，不需要提交", hidden = true)
	private Date createDate;
	// 创建者;
    @ApiModelProperty(value = "创建人，不需要提交", hidden = true)
	private String createUser;
	// 修改日期;
    @ApiModelProperty(value = "修改日期,修改数据时，传入当前数据的修改日期")
	private Date updateDate;
	// 修改者;
    @ApiModelProperty(value = "修改人，不需要提交", hidden = true)
	private String updateUser;
	
	/**
	 * <com.code>serialVersionUID</com.code>
	 */
	private static final long serialVersionUID = 1L;

	public BaseEntity() {

	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
