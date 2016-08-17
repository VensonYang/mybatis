package service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BaseDao;
import dao.BaseModel;
import service.BaseService;

@Service("baseService")
public class BaseServiceImpl<T> implements BaseService<T> {
	@Autowired
	private BaseDao baseDao;

	public Serializable save(T entity) {
		Class<?> parent = entity.getClass().getSuperclass();
		if (parent == BaseModel.class) {
			BaseModel model = (BaseModel) entity;
			model.preSave();
		}
		return baseDao.save(entity);
	}

	public void update(T entity) {
		Class<?> parent = entity.getClass().getSuperclass();
		if (parent == BaseModel.class) {
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

}
