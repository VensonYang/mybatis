package dao.model;

import javax.validation.constraints.NotNull;

import dao.BaseModel;

public class TRole extends BaseModel {
	// 主键 id
	@NotNull(message = "id不能为空", groups = { IModifyModel.class })
	private Integer id;

	// 名称 name
	@NotNull(message = "名称不能为空", groups = { IModifyModel.class, IAddModel.class })
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TRole [id=" + id + ", name=" + name + "]";
	}

}