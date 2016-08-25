package dao.model;

import dao.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * t_role表
 *
 * @author venson
 *
 * @version 2016-08-25
 **/
public class TRole extends BaseModel {
    //角色名称 role_name
    @NotBlank(message = "角色名称不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=20,message="角色名称长度必须介于1-20之间", groups = { IModifyModel.class,IAddModel.class })
    private String roleName;

    //状态 state
    private String state;

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
}