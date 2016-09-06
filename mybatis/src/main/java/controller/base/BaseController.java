package controller.base;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContext;

public abstract class BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Validator validator;

	protected boolean validateData(ReturnResult returnResult, Object data, Class<?> validGroup) {
		if (data == null) {
			logger.debug("param value is null");
			returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("参数有误，请检查参数"));
			return true;
		}
		if (data instanceof String) {
			if (data instanceof String) {
				if (StringUtils.isBlank((String) data)) {
					logger.debug("param value is empty");
					returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("参数有误，请检查参数"));
					return true;
				}
			}
		}
		Set<ConstraintViolation<Object>> constraintViolations;
		if (validGroup != null) {
			constraintViolations = validator.validate(data, validGroup);
		} else {
			constraintViolations = validator.validate(data);
		}
		if (!constraintViolations.isEmpty()) {
			for (ConstraintViolation<Object> cv : constraintViolations) {
				logger.debug(cv.getMessage());
				returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage(cv.getMessage()));
				return true;
			}
		}
		return false;
	}

	protected boolean validateData(ReturnResult returnResult, Object... datas) {
		if (datas == null) {
			logger.debug("param value is null");
			returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("参数有误，请检查参数"));
			return true;
		} else {
			for (Object o : datas) {
				if (o == null) {
					logger.debug("param value is null");
					returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("参数有误，请检查参数"));
					return true;
				}
				if (o instanceof String) {
					if (StringUtils.isBlank((String) o)) {
						logger.debug("param value is empty");
						returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("参数有误，请检查参数"));
						return true;
					}
				}
			}
			return false;
		}
	}

	protected String getMessage(HttpServletRequest request, String code) {
		RequestContext context = new RequestContext(request);
		return context.getMessage(code);
	}
}
