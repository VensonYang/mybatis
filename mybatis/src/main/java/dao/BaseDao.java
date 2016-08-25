package dao;

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

	String MAPPER_PREFIX = "Mapper.";
	String SAVE = "save";
	String UPDATE = "update";
	String DELETE = "delete";
	String GET = "get";
	String COUNT = "count";
	String FINDALL = "findAll";
	String EXCUTESQL = "dao.BaseDaoMapper.excuteSQL";

	SqlSession getSession();

	void excute(String statement);

	// <T> Serializable save(T entity);

	<T> int save(String statement, Object params);

	// <T> int update(T entity);

	<T> int update(String statement, Object params);

	// <T> void delete(Class<T> entityClass, Object id);

	<T> void delete(String statement, Object params);

	<T> T get(String statement, Object params);

	<T> T get(String statement);

	// <T> T get(Class<T> entityClass);
	//
	// <T> T getEntity(Class<T> entityClass, Object params);
	//

	<T> List<T> findAllByPage(String statement, Object params, int offset, int limit);

	// <T> List<T> findAllByPage(Class<?> entityClass, Map<String, Object>
	// params,
	// int offset, int limit);

	<T> List<T> findAllByPage(String statement, int offset, int limit);

	// <T> List<T> findAllByPage(Class<?> entityClass, int offset, int limit);

	<T> List<T> findAll(String statement, Object params);

	// <T> List<T> findAll(Class<?> entityClass, Map<String, Object> params);

	<T> List<T> findAll(String statement);

	// <T> List<T> findAll(Class<?> entityClass);

	List<Map<String, Object>> sqlFindAll(String sql);

	List<Map<String, Object>> sqlFindAllByPage(String sql, int offset, int limit);

	List<Map<String, Object>> sqlFindAllByPage(String sql, Map<String, Object> params, int offset, int limit);

	List<Map<String, Object>> sqlFindAll(String sql, Map<String, Object> params);

	Map<String, Object> sqlGet(String sql, Map<String, Object> params);

	Map<String, Object> sqlGet(String sql);

	Integer getInteger(String sql, Map<String, Object> params);

	Integer getInteger(String sql);

	Object getObject(String sql, Map<String, Object> params);

	Object getObject(String sql);

}
