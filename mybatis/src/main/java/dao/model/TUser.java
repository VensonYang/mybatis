package dao.model;

import java.util.Date;
import model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * t_user表
 *
 * @author venson
 *
 * @version 2016-08-25
 **/
public class TUser extends BaseModel {
    //用户名称 user_name
    @NotBlank(message = "用户名称不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=20,message="用户名称长度必须介于1-20之间", groups = { IModifyModel.class,IAddModel.class })
    private String userName;

    //性别 sex
    @NotBlank(message = "性别不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=4,message="性别长度必须介于1-4之间", groups = { IModifyModel.class,IAddModel.class })
    private String sex;

    //账号 user_account
    @NotBlank(message = "账号不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=32,message="账号长度必须介于1-32之间", groups = { IModifyModel.class,IAddModel.class })
    private String userAccount;

    //登陆密码 password
    @NotBlank(message = "登陆密码不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=32,message="登陆密码长度必须介于1-32之间", groups = { IModifyModel.class,IAddModel.class })
    private String password;

    //用户部门 dept_id
    private Integer deptId;

    //邮箱 email
    private String email;

    //手机 mobile
    private String mobile;

    //用户头像 head_image
    private String headImage;

    //最后登录日期 login_date
    private Date loginDate;

    //最后登录ip login_ip
    private String loginIp;

    //状态 state
    private String state;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}