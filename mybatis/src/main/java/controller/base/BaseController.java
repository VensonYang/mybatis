package controller.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.BaseService;

public class BaseController<T> {
	@Autowired
	private BaseService<T> baseService;
	private Class<T> entityClass;

	@RequestMapping("save")
	@ResponseBody
	public Serializable save(T entity, HttpServletResponse response) {
		return baseService.save(entity);
	}

	@RequestMapping("update")
	public void update(T entity, HttpServletResponse response) {
		baseService.update(entity);
	}

	@RequestMapping("delete")
	public void delete(Integer id, HttpServletResponse response) {
		baseService.delete(getEntityClass(), id);
	}

	@RequestMapping("get")
	@ResponseBody
	public T get(Integer id, HttpServletResponse response) {
		return baseService.get(getEntityClass(), id);
	}

	@RequestMapping("findAll")
	@ResponseBody
	public List<T> findAll(HttpServletResponse response) {
		return baseService.findAll(getEntityClass());
	}

	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		if (entityClass == null) {
			Type sType = getClass().getGenericSuperclass();
			if (sType instanceof ParameterizedType) {
				Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
				entityClass = (Class<T>) (generics[0]);
			}
		}
		return entityClass;
	}
}
