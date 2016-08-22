package service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.SC;
import dao.BaseDao;
import model.base.BaseModel;
import service.BaseService;

@Service("baseService")
public class BaseServiceImpl<T> implements BaseService<T> {
	@Autowired
	protected BaseDao baseDao;

	public Serializable save(T entity) {
		// 获取父类
		Class<?> parent = entity.getClass().getSuperclass();
		// 判断父类是不是模型基类
		if (parent == BaseModel.class) {
			// 保存创建日期，创建人
			BaseModel model = (BaseModel) entity;
			model.preSave();
		}
		return baseDao.save(entity);
	}

	public void update(T entity) {
		// 获取父类
		Class<?> parent = entity.getClass().getSuperclass();
		// 判断父类是不是模型基类
		if (parent == BaseModel.class) {
			// 保存创建日期，创建人
			BaseModel model = (BaseModel) entity;
			model.preUpdate();
		}
		baseDao.update(entity);
	}

	public void delete(Class<T> entityClass, Object id) {
		baseDao.delete(entityClass, id);
	}

	public T get(Class<T> entityClass, Object id) {
		return baseDao.get(entityClass, id);
	}

	public List<T> findAll(Class<T> entityClass) {
		return baseDao.findAll(entityClass);
	}

	@Override
	public List<T> findAllByPage(Class<T> entityClass, int offset, int limit) {
		return baseDao.findAllByPage(entityClass, offset, limit);
	}

	@Override
	public Map<String, Object> query(Class<T> entityClass, Map<String, String[]> params, int offset, int limit) {
		Map<String, Object> newParams = new HashMap<>();
		for (Entry<String, String[]> entry : params.entrySet()) {
			String key = entry.getKey();
			if (!key.contains("offset") && !key.contains("limit")) {
				newParams.put(entry.getKey(), entry.getValue()[0]);
			}
		}
		Map<String, Object> result = new HashMap<>();
		result.put(SC.DATA, baseDao.findAllByPage(entityClass, newParams, offset, limit));
		result.put(SC.TOTAL, baseDao.count(entityClass, newParams));
		return result;
	}

}
