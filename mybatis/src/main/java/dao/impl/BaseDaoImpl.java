package dao.impl;

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

import dao.BaseDao;
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
	public <T> Serializable save(T params) {
		return getSession().insert(buildSQL(params.getClass(), BaseDao.SAVE), params);
	}

	@Override
	public <T> int update(T params) {
		return getSession().update(buildSQL(params.getClass(), BaseDao.UPDATE), params);
	}

	@Override
	public <T> T get(Class<T> entityClass, Object id) {
		return getSession().selectOne(buildSQL(entityClass, BaseDao.GET), id);
	}

	@Override
	public <T> void delete(Class<T> entityClass, Object id) {
		getSession().delete(buildSQL(entityClass, BaseDao.DELETE), id);

	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		return findAllByPage(entityClass, -1, -1);
	}

	@Override
	public <T> List<T> findAllByPage(Class<T> entityClass, int offset, int limit) {
		if (offset == BaseDao.NO_PAGINATION || limit == BaseDao.NO_PAGINATION) {
			return getSession().selectList(buildSQL(entityClass, BaseDao.FINDALL));
		} else {
			return getSession().selectList(buildSQL(entityClass, BaseDao.FINDALL), new RowBounds(offset, limit));
		}
	}

	@Override
	public <T> List<Map<String, Object>> findAllByPage(String sql, Map<String, Object> params, int offset, int limit) {
		if (params != null) {
			sql = replaceParameter(sql, params);
		}
		if (offset == BaseDao.NO_PAGINATION || limit == BaseDao.NO_PAGINATION)
			return getSession().selectList(BaseDao.EXCUTESQL, sql);
		else
			return getSession().selectList(BaseDao.EXCUTESQL, sql, new RowBounds(offset, limit));
	}

	@Override
	public <T> List<Map<String, Object>> findAll(String sql) {
		return findAll(sql, null);
	}

	@Override
	public <T> List<Map<String, Object>> findAll(String sql, Map<String, Object> params) {
		return findAllByPage(sql, params, BaseDao.NO_PAGINATION, BaseDao.NO_PAGINATION);
	}

	@Override
	public <T> List<Map<String, Object>> findAllByPage(String sql, int offset, int limit) {
		return findAllByPage(sql, null, offset, limit);
	}

	@Override
	public <T> Map<String, Object> get(String sql, Map<String, Object> params) {
		if (params != null) {
			sql = replaceParameter(sql, params);
		}
		return getSession().selectOne(BaseDao.EXCUTESQL, sql);
	}

	@Override
	public <T> Map<String, Object> get(String sql) {
		return get(sql, null);
	}

	@Override
	public <T> Integer getInteger(String sql, Map<String, Object> params) {
		Object result = getObject(sql, params);
		if (result != null && StringUtils.isNumeric(result.toString())) {
			return Integer.parseInt(result.toString());
		}
		return null;
	}

	@Override
	public <T> Integer getInteger(String sql) {
		return getInteger(sql, null);
	}

	@Override
	public <T> Object getObject(String sql, Map<String, Object> params) {
		Map<String, Object> map = get(sql, params);
		if (null != map && map.size() == 1) {
			Collection<Object> values = map.values();
			Iterator<Object> it = values.iterator();
			return it.next();
		}
		return null;
	}

	@Override
	public <T> Object getObject(String sql) {
		return getObject(sql, null);
	}

	private String buildSQL(Class<?> t, String methodName) {
		String result = t.getName() + "." + methodName;
		return result;
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
