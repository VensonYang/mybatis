package dao.model;

import javax.validation.constraints.NotNull;

import dao.BaseModel;

public class TUser extends BaseModel {
	// 主键 id
	@NotNull(message = "id不能为空", groups = { IModifyModel.class })
	private Integer id;

	// 名称 name
	@NotNull(message = "名称不能为空", groups = { IModifyModel.class, IAddModel.class })
	private String name;

	// 账号 account
	@NotNull(message = "账号不能为空", groups = { IModifyModel.class, IAddModel.class })
	private String account;

	// 密码 password
	@NotNull(message = "密码不能为空", groups = { IModifyModel.class, IAddModel.class })
	private String password;

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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}