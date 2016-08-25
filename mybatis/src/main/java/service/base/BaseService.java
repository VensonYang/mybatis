package service.base;

import org.springframework.beans.factory.annotation.Autowired;

import dao.BaseDao;

public class BaseService {
	@Autowired
	protected BaseDao baseDao;

	protected String getStatement(Class<?> entityClass, String sqlId) {
		StringBuilder builder = new StringBuilder();
		builder.append(entityClass.getName());
		builder.append(BaseDao.MAPPER_PREFIX);
		builder.append(sqlId);
		return builder.toString();
	};
}
