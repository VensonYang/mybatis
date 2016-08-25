package dao.model;

import model.base.BaseModel;

/**
 * t_user_role表
 *
 * @author venson
 *
 * @version 2016-08-25
 **/
public class TUserRole extends BaseModel {
    //用户id user_id
    private Integer userId;

    //角色id role_id
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}