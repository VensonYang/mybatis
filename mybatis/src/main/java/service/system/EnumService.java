package service.system;

import java.util.List;
import java.util.Map;

public interface EnumService {
	/** 根据枚举类型获取枚举值 */
	List<Map<String, Object>> getEnumByTypeId(String typeId);
}
