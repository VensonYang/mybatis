package dao.model;

import javax.validation.constraints.NotNull;
import model.base.BaseModel;

/**
 * t_privileges_matrix表
 *
 * @author venson
 *
 * @version 2016-08-25
 **/
public class TPrivilegesMatrix extends BaseModel {
    //角色ID role_id
    @NotNull(message = "角色ID不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Integer roleId;

    //权限ID privileges_id
    @NotNull(message = "权限ID不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Integer privilegesId;

    //是否可创建 is_create
    @NotNull(message = "是否可创建不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Boolean isCreate;

    //是否可删除 is_delete
    @NotNull(message = "是否可删除不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Boolean isDelete;

    //是否可修改 is_modify
    @NotNull(message = "是否可修改不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Boolean isModify;

    //是否可查询 is_select
    @NotNull(message = "是否可查询不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Boolean isSelect;

    //是否可打印 is_print
    @NotNull(message = "是否可打印不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Boolean isPrint;

    //是否可导入 is_import
    @NotNull(message = "是否可导入不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Boolean isImport;

    //是否可导出 is_export
    @NotNull(message = "是否可导出不能为空", groups = { IModifyModel.class,IAddModel.class })
    private Boolean isExport;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPrivilegesId() {
        return privilegesId;
    }

    public void setPrivilegesId(Integer privilegesId) {
        this.privilegesId = privilegesId;
    }

    public Boolean getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(Boolean isCreate) {
        this.isCreate = isCreate;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getIsModify() {
        return isModify;
    }

    public void setIsModify(Boolean isModify) {
        this.isModify = isModify;
    }

    public Boolean getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Boolean isSelect) {
        this.isSelect = isSelect;
    }

    public Boolean getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Boolean isPrint) {
        this.isPrint = isPrint;
    }

    public Boolean getIsImport() {
        return isImport;
    }

    public void setIsImport(Boolean isImport) {
        this.isImport = isImport;
    }

    public Boolean getIsExport() {
        return isExport;
    }

    public void setIsExport(Boolean isExport) {
        this.isExport = isExport;
    }
}