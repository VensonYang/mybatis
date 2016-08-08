package dao;

import java.util.List;

import model.User;

public interface BaseDao {

	List<User> list();

	void add(User user);

	void update(User user);

	void delete(Integer id);

	User get(Integer id);
}
