package dao;

import java.util.Date;

public abstract class BaseModel {
	// 修改人 modifier
	protected Integer modifier;

	// 修改时间 modify_time
	protected Date modifyTime;

	// 创建人 creator
	protected Integer creator;

	// 创建时间 create_time
	protected Date createTime;

	// 备注 remark
	protected String remark;

	public Integer getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void preSave() {
		this.createTime = new Date();
		this.creator = 1;
		this.modifier = 1;
		this.modifyTime = this.createTime;
	}

	public void preUpdate() {
		this.modifier = 1;
		this.modifyTime = new Date();
	}

}
