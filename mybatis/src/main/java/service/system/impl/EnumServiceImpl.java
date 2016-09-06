package service.system.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import service.base.BaseService;
import service.system.EnumService;

@Service("enumService")
public class EnumServiceImpl extends BaseService implements EnumService {
	private static final String Mapper = "System";

	@Override
	public List<Map<String, Object>> getEnumByTypeId(String typeId) {
		return baseDao.findAll(getStatement(Mapper), typeId);
	}

}
