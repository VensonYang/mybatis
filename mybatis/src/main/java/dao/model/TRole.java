package dao.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import dao.BaseModel;

/**
 * t_role表
 *
 * @author venson
 *
 * @version 2016-08-25
 **/
public class TRole extends BaseModel {

	// 角色名称 role_name
	@NotBlank(message = "角色名称不能为空", groups = { IModifyModel.class, IAddModel.class })
	@Length(min = 1, max = 20, message = "角色名称长度必须介于1-20之间", groups = { IModifyModel.class, IAddModel.class })
	private String roleName;

	// 状态 state
	private String state;

	@NotNull(message = "权限ID不能为空", groups = { IAddModel.class })
	private int privilegesIds[];

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int[] getPrivilegesIds() {
		return privilegesIds;
	}

	public void setPrivilegesIds(int[] privilegesIds) {
		this.privilegesIds = privilegesIds;
	}

}