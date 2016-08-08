package service;

import java.util.List;

import model.User;

public interface UserService {

	List<User> showList();

	void addUser(User user);

	void updateUser(User user);

	void deleteUser(Integer id);

	User get(Integer id);
}
