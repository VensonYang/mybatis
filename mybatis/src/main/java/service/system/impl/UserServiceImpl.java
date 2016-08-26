package service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import model.system.LoginVO;
import model.system.NodeVO;
import model.system.PrivilegesVectorVO;
import service.base.BaseService;
import service.system.UserService;
import utils.common.MD5Util;

@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {

	private static final String Mapper = "System";

	@Override
	public Map<String, Object> getUserByCode(LoginVO loginVO) {
		return baseDao.get(getStatement(Mapper, getName()), loginVO);
	}

	@Override
	public Map<String, Object> getUserByAccount(String userAccount) {
		return getUserByCode(new LoginVO(userAccount));
	}

	@Override
	public void modifyPassword(int id, String pas) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("password", MD5Util.getMD5String(pas));
		params.put("id", id);
		baseDao.get(getStatement(Mapper, getName()), params);
	}

	@Override
	public List<Map<String, Object>> getUserRoleByUserId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getUserCourseByUserId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateHeadImage(String path, int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<Integer, PrivilegesVectorVO> getPrivilegesVectors(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeVO getMenuByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
