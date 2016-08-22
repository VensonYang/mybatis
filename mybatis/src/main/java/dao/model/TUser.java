package dao.model;

import model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * t_user表
 *
 * @author venson
 *
 * @version 2016-08-22
 **/
public class TUser extends BaseModel {
    //用户名 user_name
    @NotBlank(message = "用户名不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=100,message="用户名长度必须介于1-100之间", groups = { IModifyModel.class,IAddModel.class })
    private String userName;

    //账号 account
    @NotBlank(message = "账号不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=11,message="账号长度必须介于1-11之间", groups = { IModifyModel.class,IAddModel.class })
    private String account;

    //密码 password
    @NotBlank(message = "密码不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=100,message="密码长度必须介于1-100之间", groups = { IModifyModel.class,IAddModel.class })
    private String password;

    //头像 head_image
    private String headImage;

    //状态 state
    private String state;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}