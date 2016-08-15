package service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BaseDao;
import dao.model.TUser;
import service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public void addUser(TUser user) {
		baseDao.save(user);
	}

	@Override
	public void updateUser(TUser user) {
		baseDao.update(user);
	}

	@Override
	public void deleteUser(Integer id) {
		baseDao.delete(TUser.class, id);
	}

	@Override
	public List<TUser> queryUser() {
		return baseDao.getSession().selectList(TUser.class.getName() + ".findAll");
	}

	@Override
	public TUser get(Integer id) {
		return baseDao.get(TUser.class, id);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		Map<String, Object> params = new HashMap<>();
		params.put("name", "%test%");
		return baseDao.findAllByPage("select * from t_user where name like :name", params, 1, 10);
	}

	@Override
	public Map<String, Object> get() {
		Map<String, Object> params = new HashMap<>();
		params.put("id", 1);
		params.put("name", "%test%");
		return baseDao.get("select name from t_user where id=:id and name like :name", params);
	}

	@Override
	public Integer count() {
		Map<String, Object> params = new HashMap<>();
		params.put("id", 1);
		params.put("name", "%test%");
		return baseDao.getInteger("select count(*) FROM t_user");
	}

}
