package controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.CRUDController;
import controller.base.ControllerContext;
import controller.base.ReturnResult;
import dao.model.TEnum;
import service.system.EnumService;

@Controller
@ResponseBody
@RequestMapping("enum")
public class EnumController extends CRUDController<TEnum> {

	@Autowired
	private EnumService enumService;

	@RequestMapping("helloworld")
	public ReturnResult helloworld() {
		ReturnResult result = ControllerContext.getResult();
		result.setData(baseService.findAll(TEnum.class));
		return result;
	}

}
