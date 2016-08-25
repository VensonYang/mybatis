package controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.CRUDController;
import controller.base.ControllerContext;
import controller.base.ReturnResult;
import dao.model.TDepartment;
import service.system.DepartmentService;

@Controller
@ResponseBody
@RequestMapping("department")
public class DepartmentController extends CRUDController<TDepartment> {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("helloworld")
	public ReturnResult helloworld() {
		ReturnResult result = ControllerContext.getResult();
		result.setData(baseService.findAll(TDepartment.class));
		return result;
	}

}
