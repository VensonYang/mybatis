package model.system;

import java.util.Map;

import dao.model.TPrivileges;

@SuppressWarnings("serial")
public class PrivilegesVectorVO implements java.io.Serializable {
	private TPrivileges privilege;
	private Map<String, Boolean> privilegeMatrix;

	public TPrivileges getPrivilege() {
		return privilege;
	}

	public void setPrivilege(TPrivileges privilege) {
		this.privilege = privilege;
	}

	public Map<String, Boolean> getPrivilegeMatrix() {
		return privilegeMatrix;
	}

	public void setPrivilegeMatrix(Map<String, Boolean> privilegeMatrix) {
		this.privilegeMatrix = privilegeMatrix;
	}

}
