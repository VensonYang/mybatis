package dao.model;

import model.base.BaseModel;

/**
 * t_user表
 *
 * @author venson
 *
 * @version 2016-08-22
 **/
public class TUser extends BaseModel {
    //名称 user_name
    private String userName;

    //账号 account
    private String account;

    //密码 password
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