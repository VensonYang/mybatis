package service;

import java.util.List;
import java.util.Map;

import dao.model.TUser;

public interface UserService {

	List<TUser> queryUser();

	void addUser(TUser user);

	void updateUser(TUser user);

	void deleteUser(Integer id);

	TUser get(Integer id);

	List<Map<String, Object>> findAll();

	Map<String, Object> get();

	Integer count();
}
