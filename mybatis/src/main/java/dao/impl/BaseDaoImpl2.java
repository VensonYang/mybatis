package dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import dao.BaseDao2;

public class BaseDaoImpl2 implements BaseDao2 {

	@Autowired
	private SqlSessionFactory sessionFactory;

	public SqlSession getSession() {
		return this.sessionFactory.openSession();
	}

	@Override
	public <T> Serializable save(String sql, T params) {
		return this.getSession().insert(sql, params);
	}

	@Override
	public <T> int update(String sql, T params) {
		return this.getSession().update(sql, params);
	}

	@Override
	public <T> T get(String sql, Object param) {
		return this.getSession().selectOne(sql, param);
	}

	@Override
	public void delete(String sql, Object id) {
		this.getSession().delete(sql, id);
	}

	@Override
	public <T> List<T> findAll(String sql) {
		return this.getSession().selectList(sql);
	}

	@Override
	public <T> List<T> findAllByPage(String sql, int offset, int limit) {
		return findAllByPage(sql, null, offset, limit);
	}

	@Override
	public <T> List<T> findAllByPage(String sql, Object params, int offset, int limit) {
		return this.getSession().selectList(sql, params, new RowBounds(offset, limit));
	}

	@Override
	public Map<String, Object> excuteSQL(String sql) {
		return this.getSession().selectMap("dao.model.TUserMapper.excuteSQL", sql, "result");
	}

}
