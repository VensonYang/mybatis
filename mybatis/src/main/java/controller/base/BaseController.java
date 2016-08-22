package controller.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import common.StaticsConstancts;
import model.base.BaseModel.IAddModel;
import model.base.BaseModel.IModifyModel;
import service.BaseService;

public class BaseController<T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BaseService<T> baseService;
	@Autowired
	private Validator validator;
	private Class<T> entityClass;
	private String entityName;

	@RequestMapping("save")
	@ResponseBody
	public ReturnResult save(T entity) {
		ReturnResult returnResult = ControllerContext.getResult();
		if (validateData(returnResult, entity, IAddModel.class)) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setData(baseService.save(entity));
		logger.debug("save {} success", getEntityName());
		return returnResult;
	}

	@RequestMapping("update")
	@ResponseBody
	public Object update(T entity) {
		ReturnResult returnResult = ControllerContext.getResult();
		if (validateData(returnResult, entity, IModifyModel.class)) {
			return returnResult;
		}
		baseService.update(entity);
		logger.debug("update {} success", getEntityName());
		return returnResult.setStatus(StatusCode.SUCCESS);
	}

	@RequestMapping("delete")
	@ResponseBody
	public ReturnResult delete(Integer id) {
		ReturnResult returnResult = ControllerContext.getResult();
		if (validateData(returnResult, id)) {
			return returnResult;
		}
		baseService.delete(getEntityClass(), id);
		returnResult.setStatus(StatusCode.SUCCESS).setData(id);
		logger.debug("delete {} success", getEntityName());
		return returnResult;
	}

	@RequestMapping("get")
	@ResponseBody
	public ReturnResult get(Integer id) {
		ReturnResult returnResult = ControllerContext.getResult();
		if (validateData(returnResult, id)) {
			return returnResult;
		}
		returnResult.setStatus(StatusCode.SUCCESS).setData(baseService.get(getEntityClass(), id));
		logger.debug("get {} success", getEntityName());
		return returnResult;
	}

	@RequestMapping("findAll")
	@ResponseBody
	public ReturnResult findAll() {
		ReturnResult returnResult = ControllerContext.getResult();
		returnResult.setStatus(StatusCode.SUCCESS).setData(baseService.findAll(getEntityClass()));
		logger.debug("findAll {} success", getEntityName());
		return returnResult;
	}

	@RequestMapping("query")
	@ResponseBody
	public ReturnResult query(HttpServletRequest request, int offset, int limit) {
		String message = getMessage(request, "messages.error.file.not.exist");
		logger.debug(message);
		ReturnResult returnResult = ControllerContext.getResult();
		Map<String, Object> result = baseService.query(getEntityClass(), request.getParameterMap(), offset, limit);
		returnResult.setStatus(StatusCode.SUCCESS).setData(result.get(StaticsConstancts.DATA)).setTotal(result.get(StaticsConstancts.TOTAL));
		logger.debug("findAll {} success", getEntityName());
		return returnResult;
	}

	private String getEntityName() {
		if (entityName == null) {
			entityName = getEntityClass().getSimpleName();
		}
		return entityName;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		if (entityClass == null) {
			Type sType = getClass().getGenericSuperclass();
			if (sType instanceof ParameterizedType) {
				Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
				entityClass = (Class<T>) (generics[0]);
			}
		}
		return entityClass;
	}

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

	protected boolean validateData(ReturnResult returnResult, Object data) {
		return validateData(returnResult, data, null);
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
