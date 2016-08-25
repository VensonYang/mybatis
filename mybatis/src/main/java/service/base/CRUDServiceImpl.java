package service.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import common.StaticsConstancts;
import dao.BaseDao;
import dao.BaseModel;

@Service("baseService")
public class CRUDServiceImpl<T> extends BaseService implements CRUDService<T> {

	public int save(T entity) {
		Class<? extends Object> currenClass = entity.getClass();
		// 获取父类
		Class<?> parent = currenClass.getSuperclass();
		// 判断父类是不是模型基类
		boolean isModel = false;
		if (parent == BaseModel.class) {
			// 保存创建日期，创建人
			BaseModel model = (BaseModel) entity;
			model.preSave();
			isModel = true;
		}
		Integer id = baseDao.save(getStatement(currenClass, BaseDao.SAVE), entity);
		if (isModel) {
			BaseModel model = (BaseModel) entity;
			id = model.getId() == null ? model.getId() : 0;
		}
		return id;
	}

	public void update(T entity) {
		Class<? extends Object> currenClass = entity.getClass();
		// 获取父类
		Class<?> parent = currenClass.getSuperclass();
		// 判断父类是不是模型基类
		if (parent == BaseModel.class) {
			// 保存创建日期，创建人
			BaseModel model = (BaseModel) entity;
			model.preUpdate();
		}
		baseDao.update(getStatement(currenClass, BaseDao.UPDATE), entity);
	}

	public void delete(Class<T> entityClass, Object id) {
		baseDao.delete(getStatement(entityClass, BaseDao.DELETE), id);
	}

	public T get(Class<T> entityClass, Object id) {
		return baseDao.get(getStatement(entityClass, BaseDao.GET), id);
	}

	public List<T> findAll(Class<T> entityClass) {
		return baseDao.findAll(getStatement(entityClass, BaseDao.FINDALL));
	}

	@Override
	public List<T> findAll(Class<T> entityClass, int offset, int limit) {
		return baseDao.findAllByPage(getStatement(entityClass, BaseDao.FINDALL), offset, limit);
	}

	@Override
	public Map<String, Object> query(Class<T> entityClass, Map<String, String[]> oldParams, int offset, int limit) {
		// 转换参数，将Map<String, String[]> ----> Map<String, Object>
		Map<String, Object> params = new HashMap<>();
		// 获取全部参数，排除offset和limit等参数
		for (Entry<String, String[]> entry : oldParams.entrySet()) {
			String key = entry.getKey();
			if (!key.equals("offset") && !key.equals("limit")) {
				// 添加参数，取第一个value值
				params.put(entry.getKey(), entry.getValue()[0]);
			}
		}
		Map<String, Object> result = new HashMap<>();
		result.put(StaticsConstancts.DATA,
				baseDao.findAllByPage(getStatement(entityClass, BaseDao.FINDALL), params, offset, limit));
		result.put(StaticsConstancts.TOTAL, baseDao.get(getStatement(entityClass, BaseDao.COUNT), params));
		return result;
	}

}
