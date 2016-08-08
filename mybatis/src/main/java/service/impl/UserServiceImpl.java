package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BaseDao;
import model.User;
import service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public void addUser(User user) {
		baseDao.add(user);
	}

	@Override
	public void updateUser(User user) {
		baseDao.update(user);
	}

	@Override
	public void deleteUser(Integer id) {
		baseDao.delete(id);
	}

	@Override
	public List<User> showList() {

		return baseDao.list();
	}

	@Override
	public User get(Integer id) {
		return this.baseDao.get(id);
	}

}
