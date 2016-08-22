package dao.model;

import javax.validation.constraints.NotNull;
import model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * t_news表
 *
 * @author venson
 *
 * @version 2016-08-22
 **/
public class TNews extends BaseModel {
    //新闻标题 title
    @NotBlank(message = "新闻标题不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=50,message="新闻标题长度必须介于1-50之间", groups = { IModifyModel.class,IAddModel.class })
    private String title;

    //发布者 publisher
    @NotBlank(message = "发布者不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=10,message="发布者长度必须介于1-10之间", groups = { IModifyModel.class,IAddModel.class })
    private String publisher;

    //新闻类型 type_id
    @NotNull(message = "新闻类型不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Integer typeId;

    //新闻来源 source
    private String source;

    //新闻图片 image_url
    private String imageUrl;

    //新闻摘要 summary
    private String summary;

    //新闻状态 state
    @NotBlank(message = "新闻状态不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=2,message="新闻状态长度必须介于1-2之间", groups = { IModifyModel.class,IAddModel.class })
    private String state;

    //新闻内容 content
    @NotBlank(message = "新闻内容不能为空", groups = { IModifyModel.class,IAddModel.class })
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}