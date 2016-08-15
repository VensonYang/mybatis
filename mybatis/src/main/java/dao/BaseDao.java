package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

/**
 * DAO支持类
 * 
 * @author Venson
 * @version 2014-05-16
 */
// @MyBatisDao
public interface BaseDao {
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String GET = "get";
	public static final String FINDALL = "findAll";
	public static final String EXCUTESQL = "dao.model.BaseDao.excuteSQL";
	public static final int NO_PAGINATION = -1;

	SqlSession getSession();

	<T> Serializable save(T entity);

	<T> int update(T entity);

	<T> T get(Class<T> entityClass, Object id);

	<T> void delete(Class<T> entityClass, Object id);

	<T> List<T> findAll(Class<T> entityClass);

	<T> List<T> findAllByPage(Class<T> entityClass, int offset, int limit);

	<T> List<Map<String, Object>> findAll(String sql);

	<T> List<Map<String, Object>> findAllByPage(String sql, int offset, int limit);

	<T> List<Map<String, Object>> findAllByPage(String sql, Map<String, Object> params, int offset, int limit);

	<T> List<Map<String, Object>> findAll(String sql, Map<String, Object> params);

	<T> Map<String, Object> get(String sql, Map<String, Object> params);

	<T> Map<String, Object> get(String sql);

	<T> Integer getInteger(String sql, Map<String, Object> params);

	<T> Integer getInteger(String sql);

	<T> Object getObject(String sql, Map<String, Object> params);

	<T> Object getObject(String sql);
}
