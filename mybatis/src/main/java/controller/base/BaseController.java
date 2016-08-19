package controller.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.BaseModel.IAddModel;
import dao.BaseModel.IModifyModel;
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
	public Object save(T entity) {
		ReturnResult returnResult = CC.getResult();
		if (validateData(entity, returnResult, IAddModel.class)) {
			return returnResult;
		}
		logger.debug("save {} success", getEntityName());
		return returnResult.setStatus(StatusCode.SUCCESS).setRows(baseService.save(entity));
	}

	@RequestMapping("update")
	@ResponseBody
	public Object update(T entity) {
		ReturnResult returnResult = CC.getResult();
		if (validateData(entity, returnResult, IModifyModel.class)) {
			return returnResult;
		}
		baseService.update(entity);
		logger.debug("update {} success", getEntityName());
		return returnResult.setStatus(StatusCode.SUCCESS);
	}

	@RequestMapping("delete")
	@ResponseBody
	public Object delete(Integer id) {
		ReturnResult returnResult = CC.getResult();
		if (validateData(returnResult, id)) {
			return returnResult;
		}
		baseService.delete(getEntityClass(), id);
		logger.debug("delete {} success", getEntityName());
		return returnResult.setStatus(StatusCode.SUCCESS).setRows(id);
	}

	@RequestMapping("get")
	@ResponseBody
	public Object get(Integer id) {
		ReturnResult returnResult = CC.getResult();
		if (validateData(returnResult, id)) {
			return returnResult;
		}
		logger.debug("get {} success", getEntityName());
		return returnResult.setStatus(StatusCode.SUCCESS).setRows(baseService.get(getEntityClass(), id));
	}

	@RequestMapping("findAll")
	@ResponseBody
	public List<T> findAll() {
		logger.debug("findAll {} success", getEntityName());
		return baseService.findAll(getEntityClass());
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

	protected boolean validateData(Object dataObj, ReturnResult returnResult, Class<?> validGroup) {
		if (dataObj == null) {
			logger.debug("参数有误，请检查参数");
			returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("参数有误，请检查参数"));
			return true;
		}
		logger.debug(dataObj.toString());
		Set<ConstraintViolation<Object>> constraintViolations;
		if (validGroup != null) {
			constraintViolations = validator.validate(dataObj, validGroup);
		} else {
			constraintViolations = validator.validate(dataObj);
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

	protected boolean validateData(ReturnResult returnResult, Object... obj) {
		if (obj == null) {
			logger.debug("参数有误，请检查参数");
			returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("参数有误，请检查参数"));
			return true;
		} else {
			for (Object o : obj) {
				if (o == null) {
					logger.debug("参数有误，请检查参数");
					returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("参数有误，请检查参数"));
					return true;
				}
			}
		}
		return false;
	}
}
