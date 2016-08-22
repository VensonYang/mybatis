package dao.model;

import model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * t_file表
 *
 * @author venson
 *
 * @version 2016-08-22
 **/
public class TFile extends BaseModel {
    //文件名称 file_name
    @NotBlank(message = "文件名称不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=200,message="文件名称长度必须介于1-200之间", groups = { IModifyModel.class,IAddModel.class })
    private String fileName;

    //文件类型 file_type
    private String fileType;

    //文件大小 file_size
    private Integer fileSize;

    //文件路径 file_path
    @NotBlank(message = "文件路径不能为空", groups = { IModifyModel.class,IAddModel.class })
    @Length(min=1, max=200,message="文件路径长度必须介于1-200之间", groups = { IModifyModel.class,IAddModel.class })
    private String filePath;

    //状态 state
    private String state;

    //描述 file_desc
    private String fileDesc;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }
}