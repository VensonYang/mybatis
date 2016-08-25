package dao.model;

import javax.validation.constraints.NotNull;
import model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * t_news_type表
 *
 * @author venson
 *
 * @version 2016-08-25
 **/
public class TNewsType extends BaseModel {
    //新闻类型名称 type_name
    @NotBlank(message = "新闻类型名称不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=20,message="新闻类型名称长度必须介于1-20之间", groups = { IModifyModel.class,IAddModel.class })
    private String typeName;

    //排序 sort
    @NotNull(message = "排序不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Integer sort;

    //状态 state
    @NotBlank(message = "状态不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=2,message="状态长度必须介于1-2之间", groups = { IModifyModel.class,IAddModel.class })
    private String state;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}