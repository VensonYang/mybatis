package service.system.impl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import common.StaticsConstancts;
import dao.model.TPrivileges;
import model.system.NodeVO;
import model.system.PrivilegesVectorVO;
import service.base.BaseService;
import service.system.PrivilegesService;

@Service("privilegesService")
public class PrivilegesServiceImpl extends BaseService implements PrivilegesService {
	private static final String Mapper = "System";
	private Map<Integer, PrivilegesVectorVO> privilegesVectors;

	private boolean initPrivilegesVectors(int userId) {
		List<TPrivileges> privilegesResult = getPrivilegesByUserId(userId);
		if (privilegesResult != null) {
			List<Map<String, Object>> privilegeMatrixResult = getPrivilegesMatrixByUserId(userId);
			for (TPrivileges privilege : privilegesResult) {
				for (Map<String, Object> priviledgeMatrix : privilegeMatrixResult) {
					if (privilege.getId() == (Integer) priviledgeMatrix.get(StaticsConstancts.PRIVILEGES_ID)) {
						PrivilegesVectorVO privilegesVector = new PrivilegesVectorVO();
						privilegesVector.setPrivilege(privilege);
						Map<String, Boolean> privilegeMatrixMap = new HashMap<String, Boolean>();
						for (Entry<String, Object> set : priviledgeMatrix.entrySet()) {
							String key = set.getKey();
							if (!key.equals(StaticsConstancts.PRIVILEGES_ID)) {
								privilegeMatrixMap.put(key, (Boolean) set.getValue());
							}
						}
						privilegesVector.setPrivilegeMatrix(privilegeMatrixMap);
						privilegesVectors.put(privilege.getId(), privilegesVector);
					}
				}

			}
			return true;
		}
		return false;
	}

	private void iteratorPrivilege(NodeVO parentNode, Set<Integer> pids, List<TPrivileges> privileges) {
		pids.remove(parentNode.getId()); // 删除当前父ID
		for (TPrivileges privilege : privileges) {
			NodeVO node = new NodeVO(privilege.getId(), privilege.getPrivilegesName(), privilege.getUrl(),
					privilege.getPid(), privilege.getIcon());
			node.setRemark(privilege.getRemark());
			node.setTarget(privilege.getTarget());
			if (node.getPid() == parentNode.getId()) {
				parentNode.add(node); // 当前节点添加子节点
				if (pids.contains(node.getId())) { // 查询当前节点是否属于父节点
					iteratorPrivilege(node, pids, privileges);
				}
			}
		}
	}

	@Override
	public NodeVO getMenuByUserId(int userId) {
		List<TPrivileges> privileges = getPrivileges(userId, "1", null);
		Set<Integer> pids = new LinkedHashSet<Integer>();
		for (TPrivileges priviledge : privileges) {
			pids.add(priviledge.getPid());
		}
		if (pids.size() > 0) {
			NodeVO menu = new NodeVO(0, "root", null, -1, null);
			iteratorPrivilege(menu, pids, privileges);
			return menu;
		} else {
			return null;
		}
	}

	@Override
	public List<TPrivileges> getPrivilegesByUserId(int id) {
		return getPrivileges(id, null, null);
	}

	@Override
	public List<Map<String, Object>> getPrivilegesMatrixByUserId(int id) {
		return baseDao.findAll(getStatement(Mapper), id);
	}

	@Override
	public Map<Integer, PrivilegesVectorVO> getPrivilegesVectors(int userId) {
		privilegesVectors = new HashMap<Integer, PrivilegesVectorVO>();
		if (initPrivilegesVectors(userId)) {
			return privilegesVectors;
		}
		return null;
	}

	@Override
	public List<TPrivileges> getPrivilegesByRoleId(int roleId) {
		return getPrivileges(null, null, roleId);
	}

	@Override
	public NodeVO getMenu() {
		List<TPrivileges> privileges = getPrivileges(null, "1", null);
		Set<Integer> pids = new LinkedHashSet<Integer>();
		for (TPrivileges privilege : privileges) {
			pids.add(privilege.getPid());
		}
		if (pids.size() > 0) {
			NodeVO menu = new NodeVO(0, "root", null, -1, null);
			iteratorPrivilege(menu, pids, privileges);
			return menu;
		} else {
			return null;
		}
	}

	private List<TPrivileges> getPrivileges(Integer userId, String state, Integer roleId) {
		return baseDao.findAll(getStatement(Mapper),
				makeMap(new String[] { "userId", "state", "roleId" }, userId, state, roleId));
	}

}
