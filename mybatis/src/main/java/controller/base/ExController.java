package controller.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExController {

	private static final Logger logger = LoggerFactory.getLogger(ExController.class);

	@ExceptionHandler
	@ResponseBody
	public ReturnResult exceptionHandler(Exception ex) {
		ReturnResult result = ControllerContext.getResult();
		StackTraceElement st = ex.getStackTrace()[0];
		StringBuilder message = new StringBuilder();
		message.append("class:");
		message.append(st.getClassName());
		message.append(" \n method:");
		message.append(st.getMethodName());
		message.append(" \n throw:");
		message.append(ex.toString());
		result.setStatus(StatusCode.FAIL.setMessage(message.toString()));
		logger.error(message.toString());
		return result;
	}
}