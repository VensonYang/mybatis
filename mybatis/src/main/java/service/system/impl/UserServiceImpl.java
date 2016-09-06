package service.system.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.model.TUser;
import model.system.LoginVO;
import model.system.NodeVO;
import model.system.PrivilegesVectorVO;
import service.base.BaseService;
import service.base.CRUDService;
import service.system.PrivilegesService;
import service.system.UserService;
import utils.common.MD5Util;

@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	private static final String Mapper = "System";
	@Autowired
	private PrivilegesService privilegesService;
	@Autowired
	private CRUDService crudService;

	@Override
	public Map<String, Object> getUserByCode(LoginVO loginVO) {
		return baseDao.get(getStatement(Mapper), loginVO);
	}

	@Override
	public Map<String, Object> getUserByAccount(String userAccount) {
		return getUserByCode(new LoginVO(userAccount));
	}

	@Override
	public void modifyPassword(int id, String pas) {
		baseDao.get(getStatement(Mapper), makeMap(pas, id));
	}

	@Override
	public List<Map<String, Object>> getUserRoleByUserId(int id) {
		return baseDao.findAll(getStatement(Mapper), id);
	}

	@Override
	public void updateHeadImage(String path, int id) {
		baseDao.update(getStatement(Mapper), makeMap(path, id));
	}

	@Override
	public Map<Integer, PrivilegesVectorVO> getPrivilegesVectors(int userId) {
		return privilegesService.getPrivilegesVectors(userId);
	}

	@Override
	public NodeVO getMenuByUserId(int userId) {
		return privilegesService.getMenuByUserId(userId);
	}

	@Override
	public Serializable saveUserRole(int userId, int roleId) {
		return baseDao.save(getStatement(Mapper), makeMap(userId, roleId));
	}

	@Override
	public Serializable saveUser(TUser entity) {
		// 加密密码
		String password = MD5Util.getMD5String(entity.getPassword());
		entity.setPassword(password);
		Serializable id = crudService.save(entity);
		saveUserRole((int) id, entity.getRoleId());
		return id;
	}

	@Override
	public void updateUser(TUser entity) {
		// 查看是否有修改密码
		String password;
		if (StringUtils.isBlank(entity.getPassword())) {
			TUser user = crudService.get(TUser.class, entity.getId());
			password = user.getPassword();
		} else {
			password = MD5Util.getMD5String(entity.getPassword());
		}
		entity.setPassword(password);
		crudService.update(entity);
		// 查看是否有更改角色
		if (null != entity.getRoleId()) {
			saveUserRole(entity.getRoleId(), entity.getId());
		}
	}

	@Override
	public TUser getUser(int userId) {
		TUser user = crudService.get(TUser.class, userId);
		List<Map<String, Object>> roles = getUserRoleByUserId(userId);
		if (roles != null && roles.size() >= 1) {
			user.setRoleId((Integer) roles.get(0).get("id"));
		}
		return user;
	}

	@Override
	public void resetPassword(int id, String password) {
		password = MD5Util.getMD5String(password);
		baseDao.update(getStatement(Mapper), makeMap(id, password));
	}

}
