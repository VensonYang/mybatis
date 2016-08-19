package dao.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import model.base.BaseModel;

/**
 * t_role表
 *
 * @author venson
 *
 * @version 2016-08-18
 **/
public class TRole extends BaseModel {
    //名称 name
    @NotBlank(message = "名称不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=100,message="名称长度必须介于1-100之间", groups = { IModifyModel.class,IAddModel.class })
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}