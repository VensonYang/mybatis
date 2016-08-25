package dao;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
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
	public void excute(String statement) {
		getSession().select(statement, Executor.NO_RESULT_HANDLER);
	}

	// @Override
	// public <T> Serializable save(T entity) {
	// return getSession().insert(getStatement(entity.getClass(), BaseDao.SAVE),
	// entity);
	// }
	//
	// @Override
	// public <T> int update(T entity) {
	// return getSession().update(getStatement(entity.getClass(),
	// BaseDao.UPDATE), entity);
	// }
	//
	// @Override
	// public <T> void delete(Class<T> entityClass, Object id) {
	// getSession().delete(getStatement(entityClass, BaseDao.DELETE), id);
	//
	// }

	@Override
	public <T> int save(String statement, Object params) {
		return getSession().insert(statement, params);
	}

	@Override
	public <T> int update(String statement, Object params) {
		return getSession().update(statement, params);
	}

	@Override
	public <T> void delete(String statement, Object params) {
		getSession().delete(statement, params);

	}

	@Override
	public <T> T get(String statement, Object params) {
		return getSession().selectOne(statement, params);
	}

	@Override
	public <T> T get(String statement) {
		return get(statement, null);
	}

	// @Override
	// public <T> T getEntity(Class<T> entityClass, Object params) {
	// return get(entityClass, null, params);
	// }

	// @Override
	// public <T> T get(Class<T> entityClass) {
	// return get(entityClass, null, null);
	// }

	@Override
	public <T> List<T> findAllByPage(String statement, Object params, int offset, int limit) {
		return getSession().selectList(statement, params, new RowBounds(offset, limit));
	}

	// @Override
	// public <T> List<T> findAllByPage(Class<?> entityClass, Map<String,
	// Object>
	// params, int offset, int limit) {
	// return findAllByPage(entityClass, null, params, offset, limit);
	// }

	@Override
	public <T> List<T> findAllByPage(String statement, int offset, int limit) {
		return findAllByPage(statement, null, offset, limit);
	}

	// @Override
	// public <T> List<T> findAllByPage(Class<?> entityClass, int offset, int
	// limit)
	// {
	// return findAllByPage(entityClass, null, null, offset, limit);
	// }

	@Override
	public <T> List<T> findAll(String statement, Object params) {
		final RowBounds defaultBounds = RowBounds.DEFAULT;
		return findAllByPage(statement, params, defaultBounds.getOffset(), defaultBounds.getLimit());
	}

	// @Override
	// public <T> List<T> findAll(Class<?> entityClass, Map<String, Object>
	// params) {
	// final RowBounds defaultBounds = RowBounds.DEFAULT;
	// return findAllByPage(entityClass, null, params,
	// defaultBounds.getOffset(),
	// defaultBounds.getLimit());
	// }

	@Override
	public <T> List<T> findAll(String statement) {
		return findAll(statement, null);
	}

	// @Override
	// public <T> List<T> findAll(Class<?> entityClass) {
	// return findAll(entityClass, null, null);
	// }

	@Override
	public List<Map<String, Object>> sqlFindAllByPage(String sql, Map<String, Object> params, int offset, int limit) {
		if (params != null) {
			sql = replaceParameter(sql, params);
		}
		if (offset == -1 || limit == -1)
			return getSession().selectList(BaseDao.EXCUTESQL, sql);
		else
			return getSession().selectList(BaseDao.EXCUTESQL, sql, new RowBounds(offset, limit));
	}

	@Override
	public List<Map<String, Object>> sqlFindAll(String sql) {
		return findAll(sql, null);
	}

	@Override
	public List<Map<String, Object>> sqlFindAll(String sql, Map<String, Object> params) {
		return findAllByPage(sql, params, -1, -1);
	}

	@Override
	public List<Map<String, Object>> sqlFindAllByPage(String sql, int offset, int limit) {
		return findAllByPage(sql, null, offset, limit);
	}

	@Override
	public Map<String, Object> sqlGet(String sql, Map<String, Object> params) {
		if (params != null) {
			sql = replaceParameter(sql, params);
		}
		return getSession().selectOne(BaseDao.EXCUTESQL, sql);
	}

	@Override
	public Map<String, Object> sqlGet(String sql) {
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
