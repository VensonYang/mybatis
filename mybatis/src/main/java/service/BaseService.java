package service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {

	List<T> findAll(Class<T> entityClass);

	Serializable save(T entity);

	void update(T entity);

	void delete(Class<T> entityClass, Object id);

	T get(Class<T> entityClass, Object id);

}
