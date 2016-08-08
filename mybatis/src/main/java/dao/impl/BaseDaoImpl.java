package dao.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.BaseDao;
import model.User;

@Repository("baseDao")
public class BaseDaoImpl implements BaseDao {

	private AtomicInteger autoIncrement = new AtomicInteger(1);
	List<User> list = new LinkedList<User>();
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public SqlSession getSession() {
		return this.sqlSessionFactory.openSession();
	}

	@Override
	public void add(User user) {
		user.setId(autoIncrement.getAndIncrement());
		list.add(user);
	}

	@Override
	public void update(User user) {
		for (User u : list) {
			if (u.getId() == user.getId()) {
				u.setName(user.getName());
			}
		}
	}

	@Override
	public void delete(Integer id) {
		Iterator<User> it = list.iterator();
		while (it.hasNext()) {
			User user = (User) it.next();
			if (user.getId() == id) {
				it.remove();
			}

		}

	}

	@Override
	public List<User> list() {
		return this.list;
	}

	@Override
	public User get(Integer id) {
		return this.getSession().selectOne("TUser.selectById", id);
	}

}
