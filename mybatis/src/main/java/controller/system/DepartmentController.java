package controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.CRUDController;
import controller.base.ControllerContext;
import controller.base.ReturnResult;
import controller.base.StatusCode;
import dao.model.TDepartment;
import service.system.DepartmentService;

@Controller
@ResponseBody
@RequestMapping("department")
public class DepartmentController extends CRUDController<TDepartment> {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "getAllDepartment")
	public ReturnResult getAllDepartment() {
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setData(departmentService.getAllDepartment());
		logger.debug("getAllDepartment success");
		return returnResult;
	}

}
