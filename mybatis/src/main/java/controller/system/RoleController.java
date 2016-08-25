package controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.CRUDController;
import controller.base.ControllerContext;
import controller.base.ReturnResult;
import dao.model.TRole;
import service.system.RoleService;

@Controller
@ResponseBody
@RequestMapping("role")
public class RoleController extends CRUDController<TRole> {

	@Autowired
	private RoleService roleService;

	@RequestMapping("helloworld")
	public ReturnResult helloworld() {
		ReturnResult result = ControllerContext.getResult();
		result.setData(baseService.findAll(TRole.class));
		return result;
	}

}
