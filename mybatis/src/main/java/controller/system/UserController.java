package controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.CRUDController;
import controller.base.ControllerContext;
import controller.base.ReturnResult;
import dao.model.TUser;
import service.system.UserService;

@Controller
@ResponseBody
@RequestMapping("user")
public class UserController extends CRUDController<TUser> {

	@Autowired
	private UserService userService;

	@RequestMapping("helloworld")
	public ReturnResult helloworld() {
		ReturnResult result = ControllerContext.getResult();
		result.setData(baseService.findAll(TUser.class));
		return result;
	}

}
