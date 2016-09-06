package service.system.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import service.base.BaseService;
import service.system.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl extends BaseService implements DepartmentService {
	private static final String Mapper = "System";

	@Override
	public List<Map<String, Object>> getAllDepartment() {
		return baseDao.findAll(getStatement(Mapper));
	}

}
