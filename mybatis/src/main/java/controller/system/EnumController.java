package controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.base.CRUDController;
import controller.base.ControllerContext;
import controller.base.ReturnResult;
import controller.base.StatusCode;
import dao.model.TEnum;
import service.system.EnumService;

@Controller
@ResponseBody
@RequestMapping("enum")
public class EnumController extends CRUDController<TEnum> {

	@Autowired
	private EnumService enumService;

	@RequestMapping(value = "getEnumByTypeId")
	public ReturnResult getEnumByTypeId(String typeId) {
		ReturnResult returnResult = ControllerContext.getResult();
		if (validateData(returnResult, typeId)) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setData(enumService.getEnumByTypeId(typeId));
		logger.debug("getEnumByTypeId success");
		return returnResult;
	}

}
