package dao.model;

import dao.BaseModel;
import javax.validation.constraints.NotNull;

/**
 * t_role_privileges表
 *
 * @author venson
 *
 * @version 2016-08-25
 **/
public class TRolePrivileges extends BaseModel {
    //权限ID privileges_id
    @NotNull(message = "权限ID不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Integer privilegesId;

    //角色ID role_id
    @NotNull(message = "角色ID不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Integer roleId;

    public Integer getPrivilegesId() {
        return privilegesId;
    }

    public void setPrivilegesId(Integer privilegesId) {
        this.privilegesId = privilegesId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}