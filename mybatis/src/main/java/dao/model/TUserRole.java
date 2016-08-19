package dao.model;

import javax.validation.constraints.NotNull;

import model.base.BaseModel;

/**
 * t_user_role表
 *
 * @author venson
 *
 * @version 2016-08-18
 **/
public class TUserRole extends BaseModel {
    //用户ID role_id
    @NotNull(message = "用户ID不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Integer roleId;

    //角色ID user_id
    @NotNull(message = "角色ID不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Integer userId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}