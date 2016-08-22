package dao.model;

import model.base.BaseModel;

/**
 * t_role表
 *
 * @author venson
 *
 * @version 2016-08-22
 **/
public class TRole extends BaseModel {
    //名称 name
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}