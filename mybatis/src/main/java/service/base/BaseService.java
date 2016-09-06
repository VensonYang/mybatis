package service.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import dao.BaseDao;

public abstract class BaseService {
	@Autowired
	protected BaseDao baseDao;

	protected String getStatement(Class<?> entityClass, String sqlId) {
		StringBuilder builder = new StringBuilder();
		builder.append(entityClass.getName());
		builder.append(BaseDao.MAPPER_PREFIX);
		builder.append(sqlId);
		return builder.toString();
	}

	protected String getStatement(String mapper, String sqlId) {
		StringBuilder builder = new StringBuilder();
		builder.append(mapper);
		builder.append(BaseDao.MAPPER_PREFIX);
		builder.append(sqlId);
		return builder.toString();
	}

	protected String getStatement(String mapper) {
		return getStatement(mapper, new Throwable().getStackTrace()[1].getMethodName());
	}

	protected Map<String, Object> makeMap(String[] names, Object... values) {
		int size = names.length;
		if (size != values.length) {
			throw new RuntimeException("names长度和values长度不一致");
		}
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < size; i++) {
			map.put(names[i], values[i]);
		}
		return map;
	}

	protected Map<String, Object> makeMap(Object... values) {
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < values.length; i++) {
			map.put("arg" + i, values[i]);
		}
		System.out.println(map);
		return map;
	}

}
