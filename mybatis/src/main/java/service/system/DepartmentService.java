package service.system;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
	/* 获取所有的部门 **/
	List<Map<String, Object>> getAllDepartment();
}
