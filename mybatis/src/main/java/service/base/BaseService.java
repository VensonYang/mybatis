package service.base;

import org.springframework.beans.factory.annotation.Autowired;

import dao.BaseDao;

public class BaseService {
	@Autowired
	protected BaseDao baseDao;
}
