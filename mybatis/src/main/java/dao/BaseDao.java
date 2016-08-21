package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

/**
 * DAO支持类
 * 
 * @author Venson
 * @version 2014-05-16
 */
// @MyBatisDao
public interface BaseDao {

	String MAPPER_PREFIX = "Mapper.";
	String SAVE = "save";
	String UPDATE = "update";
	String DELETE = "delete";
	String GET = "get";
	String COUNT = "count";
	String FINDALL = "findAll";
	String EXCUTESQL = "excuteSQL";

	SqlSession getSession();

	public String getStatement(Class<?> t, String id);

	<T> Serializable save(T entity);

	<T> int update(T entity);

	<T> Long count(Class<T> entityClass);

	<T> Long count(Class<T> entityClass, @Param("params") Map<String, Object> params);

	<T> T get(Class<T> entityClass, Object id);

	<T> void delete(Class<T> entityClass, Object id);

	<T> List<T> findAll(Class<T> entityClass);

	<T> List<T> findAll(Class<T> entityClass, Map<String, Object> params);

	<T> List<T> findAllByPage(Class<T> entityClass, int offset, int limit);

	<T> List<T> findAllByPage(Class<T> entityClass, Map<String, Object> params, int offset, int limit);

	List<Map<String, Object>> findAll(String sql);

	List<Map<String, Object>> findAllByPage(String sql, int offset, int limit);

	List<Map<String, Object>> findAllByPage(String sql, Map<String, Object> params, int offset, int limit);

	List<Map<String, Object>> findAll(String sql, Map<String, Object> params);

	Map<String, Object> get(String sql, Map<String, Object> params);

	Map<String, Object> get(String sql);

	Integer getInteger(String sql, Map<String, Object> params);

	Integer getInteger(String sql);

	Object getObject(String sql, Map<String, Object> params);

	Object getObject(String sql);

}
