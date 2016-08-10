package dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao {

	<T> Serializable save(String sql, T params);

	<T> int update(String sql, T params);

	<T> T get(String sql, Object param);

	void delete(String sql, Object id);

	<T> List<T> findAll(String sql);

	<T> List<T> findAllByPage(String sql, int offset, int limit);

	<T> List<T> findAllByPage(String sql, Object params, int offset, int limit);
}
