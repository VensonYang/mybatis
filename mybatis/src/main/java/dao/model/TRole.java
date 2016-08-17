package dao.model;

import dao.BaseModel;

public class TRole extends BaseModel {
	// 主键 id
	private Integer id;

	// 名称 name
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