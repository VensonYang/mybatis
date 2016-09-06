package controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.CRUDController;
import controller.base.ControllerContext;
import controller.base.ReturnResult;
import controller.base.StatusCode;
import dao.BaseModel.IAddModel;
import dao.model.TRole;
import service.system.RoleService;

@Controller
@ResponseBody
@RequestMapping("role")
public class RoleController extends CRUDController<TRole> {

	@Autowired
	private RoleService roleService;

	@RequestMapping("showRole")
	public ReturnResult showRole() {
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setData(roleService.findRoles());
		logger.debug("showRole success");
		return returnResult;
	}

	@RequestMapping(value = "getRolePrivileges")
	public ReturnResult getRolePrivileges(Integer roleId) {
		ReturnResult returnResult = ControllerContext.getResult();
		if (validateData(returnResult, roleId)) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setData(roleService.getPrivilegesByRoleId(roleId));
		logger.debug("getPrivilegesByRoleId success");
		return returnResult;
	}

	@RequestMapping(value = "addRolePrivileges")
	public ReturnResult addRolePrivileges(TRole role) {
		ReturnResult returnResult = ControllerContext.getResult();
		if (validateData(returnResult, role, IAddModel.class)) {
			return returnResult;
		}
		roleService.saveRolePrivileges(role);
		returnResult.setStatus(StatusCode.SUCCESS);
		logger.debug("saveRolePrivileges success");
		return returnResult;
	}

}
