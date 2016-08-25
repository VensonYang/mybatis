package dao.model;

import model.base.BaseModel;

/**
 * t_department表
 *
 * @author venson
 *
 * @version 2016-08-25
 **/
public class TDepartment extends BaseModel {
    //名称 dept_name
    private String deptName;

    //上级部门 pid
    private Integer pid;

    //部门编号 dept_no
    private String deptNo;

    //状态 state
    private String state;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}