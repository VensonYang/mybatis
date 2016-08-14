package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import annotation.MyBatisDao;

@MyBatisDao
public interface BaseDao {

	<T> Serializable save(T params);

	<T> int update(T params);

	<T> T get(Object param);

	void delete(Object id);

	<T> List<T> findAll();

	<T> List<T> findAllByPage(int offset, int limit);

	<T> List<T> findAllByPage(Object params, int offset, int limit);

	Map<String, Object> excuteSQL(String sql);
}
