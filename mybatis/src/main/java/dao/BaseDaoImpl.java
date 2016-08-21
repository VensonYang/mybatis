package dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utils.common.DateFormaterUtil;

@Repository("baseDao")
public class BaseDaoImpl implements BaseDao {

	@Autowired
	private SqlSessionFactory sessionFactory;

	@Override
	public SqlSession getSession() {
		return sessionFactory.openSession();
	}

	@Override
	public <T> Serializable save(T entity) {
		return getSession().insert(getStatement(entity.getClass(), BaseDao.SAVE), entity);
	}

	@Override
	public <T> int update(T entity) {
		return getSession().update(getStatement(entity.getClass(), BaseDao.UPDATE), entity);
	}

	@Override
	public <T> Long count(Class<T> entityClass) {
		return count(entityClass, null);
	}

	@Override
	public <T> Long count(Class<T> entityClass, Map<String, Object> params) {
		return getSession().selectOne(getStatement(entityClass, BaseDao.COUNT), params);
	}

	@Override
	public <T> T get(Class<T> entityClass, Object id) {
		return getSession().selectOne(getStatement(entityClass, BaseDao.GET), id);
	}

	@Override
	public <T> void delete(Class<T> entityClass, Object id) {
		getSession().delete(getStatement(entityClass, BaseDao.DELETE), id);

	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		return findAll(entityClass, null);
	}

	@Override
	public <T> List<T> findAllByPage(Class<T> entityClass, int offset, int limit) {
		return findAllByPage(entityClass, null, offset, limit);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass, Map<String, Object> params) {
		return findAllByPage(entityClass, params, -1, -1);
	}

	@Override
	public <T> List<T> findAllByPage(Class<T> entityClass, Map<String, Object> params, int offset, int limit) {
		final boolean isPage = (offset != -1 && limit != -1);
		if (isPage) {
			return getSession().selectList(getStatement(entityClass, BaseDao.FINDALL), params,
					new RowBounds(offset, limit));
		} else {
			return getSession().selectList(getStatement(entityClass, BaseDao.FINDALL), params);
		}
	}

	@Override
	public List<Map<String, Object>> findAllByPage(String sql, Map<String, Object> params, int offset, int limit) {
		if (params != null) {
			sql = replaceParameter(sql, params);
		}
		if (offset == -1 || limit == -1)
			return getSession().selectList(getStatement(BaseDao.class, BaseDao.EXCUTESQL), sql);
		else
			return getSession().selectList(getStatement(BaseDao.class, BaseDao.EXCUTESQL), sql,
					new RowBounds(offset, limit));
	}

	@Override
	public List<Map<String, Object>> findAll(String sql) {
		return findAll(sql, null);
	}

	@Override
	public List<Map<String, Object>> findAll(String sql, Map<String, Object> params) {
		return findAllByPage(sql, params, -1, -1);
	}

	@Override
	public List<Map<String, Object>> findAllByPage(String sql, int offset, int limit) {
		return findAllByPage(sql, null, offset, limit);
	}

	@Override
	public Map<String, Object> get(String sql, Map<String, Object> params) {
		if (params != null) {
			sql = replaceParameter(sql, params);
		}
		return getSession().selectOne(getStatement(BaseDao.class, BaseDao.EXCUTESQL), sql);
	}

	@Override
	public Map<String, Object> get(String sql) {
		return get(sql, null);
	}

	@Override
	public Integer getInteger(String sql, Map<String, Object> params) {
		Object result = getObject(sql, params);
		if (result != null && StringUtils.isNumeric(result.toString())) {
			return Integer.parseInt(result.toString());
		}
		return null;
	}

	@Override
	public Integer getInteger(String sql) {
		return getInteger(sql, null);
	}

	@Override
	public Object getObject(String sql, Map<String, Object> params) {
		Map<String, Object> map = get(sql, params);
		if (null != map && map.size() == 1) {
			Collection<Object> values = map.values();
			Iterator<Object> it = values.iterator();
			return it.next();
		}
		return null;
	}

	@Override
	public Object getObject(String sql) {
		return getObject(sql, null);
	}

	@Override
	public String getStatement(Class<?> t, String id) {
		StringBuilder builder = new StringBuilder();
		builder.append(t.getName());
		builder.append(MAPPER_PREFIX);
		builder.append(id);
		return builder.toString();
	}

	private String replaceParameter(String sql, Map<String, Object> params) {
		for (Entry<String, Object> entry : params.entrySet()) {
			Object o = entry.getValue();
			if (o instanceof String) {
				o = "'" + o + "'";
			} else if (o instanceof Date) {
				o = "'" + DateFormaterUtil.dateToString(DateFormaterUtil.FORMART4, (Date) o) + "'";
			}
			sql = sql.replaceAll(":" + entry.getKey(), o.toString());
		}
		return sql;
	}

}
