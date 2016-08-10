package service;

import java.util.List;

import dao.model.TUser;

public interface UserService {

	List<TUser> queryUser();

	void addUser(TUser user);

	void updateUser(TUser user);

	void deleteUser(Integer id);

	TUser get(Integer id);
}
