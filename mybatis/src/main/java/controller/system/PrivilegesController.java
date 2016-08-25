package controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.CRUDController;
import controller.base.ControllerContext;
import controller.base.ReturnResult;
import dao.model.TPrivileges;
import service.system.PrivilegesService;

@Controller
@ResponseBody
@RequestMapping("privileges")
public class PrivilegesController extends CRUDController<TPrivileges> {

	@Autowired
	private PrivilegesService privilegesService;

	@RequestMapping("helloworld")
	public ReturnResult helloworld() {
		logger.debug("sdsd");
		ReturnResult result = ControllerContext.getResult();
		result.setData(baseService.findAll(TPrivileges.class));
		return result;
	}

}
