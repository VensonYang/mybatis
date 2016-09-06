package service.system;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import dao.model.TPrivileges;
import dao.model.TRole;

public interface RoleService {
	/**
	 * 获取全部角色
	 * 
	 */
	List<Map<String, Object>> findRoles();

	List<TPrivileges> getPrivilegesByRoleId(int roleId);

	/**
	 * 增加角色权限
	 * 
	 * @param role
	 *            添加对象
	 */
	Serializable saveRolePrivileges(TRole role);
}
