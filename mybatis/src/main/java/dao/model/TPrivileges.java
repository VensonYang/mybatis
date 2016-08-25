package dao.model;

import model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * t_privileges表
 *
 * @author venson
 *
 * @version 2016-08-25
 **/
public class TPrivileges extends BaseModel {
    //名称 privileges_name
    private String privilegesName;

    //权限控制地址 url
    @NotBlank(message = "权限控制地址不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=100,message="权限控制地址长度必须介于1-100之间", groups = { IModifyModel.class,IAddModel.class })
    private String url;

    //目标 target
    private String target;

    //父节点 pid
    private Integer pid;

    //图标 icon
    private String icon;

    //是否可用 state
    @NotBlank(message = "是否可用不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=2,message="是否可用长度必须介于1-2之间", groups = { IModifyModel.class,IAddModel.class })
    private String state;

    public String getPrivilegesName() {
        return privilegesName;
    }

    public void setPrivilegesName(String privilegesName) {
        this.privilegesName = privilegesName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}