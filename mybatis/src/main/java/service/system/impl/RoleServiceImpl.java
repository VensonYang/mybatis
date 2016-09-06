package service.system.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.model.TPrivileges;
import dao.model.TRole;
import service.base.BaseService;
import service.system.PrivilegesService;
import service.system.RoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseService implements RoleService {
	private static final String Mapper = "System";
	@Autowired
	private PrivilegesService privilegesService;

	@Override
	public List<Map<String, Object>> findRoles() {
		return baseDao.findAll(getStatement(Mapper));
	}

	@Override
	public List<TPrivileges> getPrivilegesByRoleId(int roleId) {
		return privilegesService.getPrivilegesByRoleId(roleId);
	}

	@Override
	public Serializable saveRolePrivileges(TRole role) {
		Integer roleId = role.getId();
		deleteRolePrivileges(roleId);
		int[] ids = role.getPrivilegesIds();
		Map<String, Object> map = makeMap(new String[] { "roleId", "ids" }, roleId, ids);
		saveRolePrivileges(map);
		saveRolePrivilegesMatrix(map);
		return roleId;
	}

	private void deleteRolePrivileges(int roleId) {
		baseDao.delete(getStatement(Mapper), roleId);
	}

	private void saveRolePrivileges(Map<String, Object> map) {
		baseDao.delete(getStatement(Mapper), map);
	}

	private void saveRolePrivilegesMatrix(Map<String, Object> map) {
		baseDao.delete(getStatement(Mapper), map);
	}

}
