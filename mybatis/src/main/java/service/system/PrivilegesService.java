package service.system;

import java.util.List;
import java.util.Map;

import dao.model.TPrivileges;
import model.system.NodeVO;
import model.system.PrivilegesVectorVO;

public interface PrivilegesService {

	/**
	 * 根据用户Id获取用户权限矩阵集
	 * 
	 * @param userId
	 *            用户Id
	 */
	Map<Integer, PrivilegesVectorVO> getPrivilegesVectors(int userId);

	/**
	 * 根据用户Id获取用户权限
	 * 
	 * @param userId
	 *            用户Id
	 */
	List<TPrivileges> getPrivilegesByUserId(int userId);

	/**
	 * 根据角色Id获取角色权限
	 * 
	 * @param roleId
	 *            角色Id
	 */
	List<TPrivileges> getPrivilegesByRoleId(int roleId);

	/**
	 * 根据用户Id获取用户菜单
	 * 
	 * @param userId
	 *            用户Id
	 */
	NodeVO getMenuByUserId(int userId);

	/**
	 * 获取所有菜单
	 */
	NodeVO getMenu();

	/**
	 * 根据用户Id获取用户权限矩阵
	 * 
	 * @param id
	 *            用户Id
	 */
	List<Map<String, Object>> getPrivilegesMatrixByUserId(int id);
}
