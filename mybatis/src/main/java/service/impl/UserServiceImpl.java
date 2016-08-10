package service.impl;

import java.util.List;

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
		baseDao.save("dao.model.TUserMapper.insertSelective", user);
	}

	@Override
	public void updateUser(TUser user) {
		baseDao.update("dao.model.TUserMapper.updateByPrimaryKeySelective", user);
	}

	@Override
	public void deleteUser(Integer id) {
		baseDao.delete("dao.model.TUserMapper.deleteByPrimaryKey", id);
	}

	@Override
	public List<TUser> showList() {
		return baseDao.findAllByPage("dao.model.TUserMapper.selectList", 1, 2);
	}

	@Override
	public TUser get(Integer id) {
		return this.baseDao.get("dao.model.TUserMapper.selectByPrimaryKey", id);
	}

}
