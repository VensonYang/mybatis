package dao.model;

import model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * t_enumbank表
 *
 * @author venson
 *
 * @version 2016-08-22
 **/
public class TEnumbank extends BaseModel {
    //类型编号 type_id
    @NotBlank(message = "类型编号不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=8,message="类型编号长度必须介于1-8之间", groups = { IModifyModel.class,IAddModel.class })
    private String typeId;

    //类型名称 type_name
    @NotBlank(message = "类型名称不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=30,message="类型名称长度必须介于1-30之间", groups = { IModifyModel.class,IAddModel.class })
    private String typeName;

    //枚举ID enum_id
    @NotBlank(message = "枚举ID不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=8,message="枚举ID长度必须介于1-8之间", groups = { IModifyModel.class,IAddModel.class })
    private String enumId;

    //枚举值 enum_value
    @NotBlank(message = "枚举值不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=120,message="枚举值长度必须介于1-120之间", groups = { IModifyModel.class,IAddModel.class })
    private String enumValue;

    //状态 state
    private String state;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEnumId() {
        return enumId;
    }

    public void setEnumId(String enumId) {
        this.enumId = enumId;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}