package controller.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.alibaba.fastjson.JSONObject;

import common.SC;
import utils.common.NetworkUtil;

/**
 * controller助手，该类包含了controller类常用的一些常量以及方法
 * 
 * @author Venson Yang
 */
public final class CH {
	// 文件类型
	public static final String CONTENT_TYPE_HTML = "text/html";
	public static final String CONTENT_TYPE_IMAGE_JPG = "image/jpeg";
	public static final String CONTENT_TYPE_IMAGE_PNG = "image/png";
	public final static String CONTENT_TYPE_STREAM = "application/octet-stream";
	public final static String CONTENT_TYPE_JSON = "application/json";
	public final static String CONTENT_TYPE_PDF = "application/pdf";
	public final static String CONTENT_TYPE_XML = "text/xml";
	public final static String CONTENT_TYPE_EXCEL = "application/excel";
	public final static String CONTENT_TYPE_EXCEL_93 = "application/vnd.ms-excel";
	public final static String CONTENT_TYPE_EXCEL_97 = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public final static String CONTENT_TYPE_WORD_93 = "application/msword";
	public final static String CONTENT_TYPE_WORD_97 = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	// 附件下载格式
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String CONTENT_DISPOSITION_INLINE = "inline";
	public static final String CONTENT_DISPOSITION_ATTACHMENT = "attachment";
	// 上传文件路径
	public static final String ATTACHMENT_UPLOAD_PATH = "/upload/attachment";
	public static final String ATTACHMENT_ROOT_PATH = "/";
	public static final String EXCEL_UPLOAD_PATH = "/upload/excel";
	public static final String HTML_UPLOAD_PATH = "/upload/html";
	public static final String UPLOAD_PREVIEW_PATH = "/upload/preview";
	public static final String WORD_UPLOAD_PATH = "/upload/word";
	public static final String IMAGE_UPLOAD_PATH = "/upload/image";
	public static final String HEAD_IMAGE_UPLOAD_PATH = "/upload/image/headImage";

	private static final Logger logger = LoggerFactory.getLogger(CH.class);

	private static Validator validator;
	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * 在国际化资源文件中获取指定键值的value值
	 * 
	 * @param key
	 *            键值
	 * @return 键对应的值
	 */
	public static String getMessage(String key) {
		Locale locale = CC.getRequest().getLocale();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("message", locale);
		String msg = resourceBundle.getString(key);
		if (msg != null)
			return msg;
		return null;
	}

	public static String getUploadPath() {
		return getUploadPath(null);
	}

	/**
	 * 获取上传文件的根路径
	 * 
	 * @param path
	 *            相对路径
	 * @return 上传文件根路径
	 */
	public static String getUploadPath(String path) {
		HttpServletRequest request = CC.getRequest();
		if (path == null) {
			return request.getServletContext().getRealPath("/upload") + File.separator;
		}
		if (path == ATTACHMENT_ROOT_PATH)
			return request.getServletContext().getRealPath(path);
		else
			return request.getServletContext().getRealPath(path) + File.separator;

	}

	/**
	 * 获取带部署上下文的域名
	 * 
	 */
	public static String getDeployDomain() {
		HttpServletRequest request = CC.getRequest();
		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length())
				.append(request.getServletContext().getContextPath()).append("/").toString();
		return tempContextUrl;
	}

	/**
	 * 获取域名
	 * 
	 */
	public static String getDomain() {
		HttpServletRequest request = CC.getRequest();
		StringBuffer url = request.getRequestURL();
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/")
				.toString();
		return tempContextUrl;
	}

	public static String getDownloadPath() {
		HttpServletRequest request = CC.getRequest();
		return request.getServletContext().getRealPath("/download") + File.separator;
	}

	/**
	 * 获取打印文件的根路径
	 * 
	 * @return 打印文件根路径
	 */
	public static String getPrintPath() {
		HttpServletRequest request = CC.getRequest();
		return request.getServletContext().getRealPath("/prints") + File.separator;
	}

	/**
	 * 
	 * @Title: processFileName
	 * 
	 * @Description: ie,chrom,firfox下处理文件名显示乱码
	 */
	public static String processFileName(String fileNames) {
		HttpServletRequest request = CC.getRequest();
		String codedfilename = null;
		try {
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {// ie

				String name = java.net.URLEncoder.encode(fileNames, "UTF8");

				codedfilename = name;
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等

				codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codedfilename;
	}

	/**
	 * 输入下载文件
	 * 
	 * @param contentType
	 *            文件类型
	 * @param header
	 *            文件打开形式以及文件名
	 * @param data
	 *            文件数据
	 * @return 打印文件根路径
	 */
	public static void makeAttachment(String contentType, String header[], byte[] data) throws IOException {
		HttpServletResponse response = CC.getResponse();
		response.setContentType(contentType);
		response.setHeader(CONTENT_DISPOSITION, header[0] + ";fileName=" + processFileName(header[1]));
		ServletOutputStream stream = response.getOutputStream();
		stream.write(data);
		stream.flush();
		stream.close();
	}

	public static void makeAttachment(String contentType, String[] header, InputStream data) throws IOException {
		makeAttachment(contentType, header, IOUtils.toByteArray(data));
	}

	public static void makeAttachment(String contentType, String[] header, String filePath) throws IOException {
		makeAttachment(contentType, header, IOUtils.toByteArray(new FileInputStream(filePath)));
	}

	/**
	 * 渲染JSON数据
	 * 
	 * @param contentType
	 *            文件类型
	 * @param error
	 *            信息
	 * @param logger
	 *            日志
	 */
	public static boolean renderJSON(String contentType, String error, Logger logger) throws IOException {
		contentType = StringUtils.defaultString(contentType, CONTENT_TYPE_JSON);
		ReturnResult returnResult = CC.getResult();
		HttpServletResponse response = CC.getResponse();
		returnResult.setStatus(StatusCode.NO_ACCESS.setMessage(error));
		response.setContentType(contentType + "; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(JSONObject.toJSON(returnResult));
		out.close();
		if (logger != null)
			logger.info("IP[" + NetworkUtil.getIpAddress(CC.getRequest()) + "]" + error);

		return false;
	}

	public static boolean renderJSON(String error, Logger logger) throws IOException {
		return renderJSON(null, error, logger);
	}

	public static void renderJSON(String contentType) throws IOException {
		contentType = StringUtils.defaultString((String) contentType, CONTENT_TYPE_JSON);
		ReturnResult returnResult = CC.getResult();
		HttpServletResponse response = CC.getResponse();
		response.setContentType(contentType + "; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(JSONObject.toJSON(returnResult));
		out.close();
	}

	public static void renderJSON(String contentType, Object obj) throws IOException {
		contentType = StringUtils.defaultString((String) contentType, CONTENT_TYPE_JSON);
		HttpServletResponse response = CC.getResponse();
		response.setContentType(contentType + "; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(JSONObject.toJSON(obj));
		out.close();
	}

	/**
	 * 生成文件名
	 * 
	 * @param ext
	 *            文件扩展名
	 */
	public static String makeFileName(String ext) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(java.util.UUID.randomUUID().toString());
		buffer.append("-");
		buffer.append(System.currentTimeMillis());
		if (ext != null) {
			buffer.append(ext);
		}
		return buffer.toString();
	}

	public static String bulidFileName() {
		return makeFileName(null);
	}

	public static Integer getUserId() {
		HttpSession session = CC.getSession();
		Object userId = session.getAttribute(SC.USER_ID);
		if (userId != null) {
			return (Integer) userId;
		}
		return null;
	}

	public static String getUserAccount() {
		HttpSession session = CC.getSession();
		Object userAccount = session.getAttribute(SC.USER_ACCOUNT);
		if (userAccount != null) {
			return (String) userAccount;
		}
		return null;
	}

	public static String getUserName() {
		HttpSession session = CC.getSession();
		Object userId = session.getAttribute(SC.USER_NAME);
		if (userId != null) {
			return userId.toString();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static String getSubject() {
		HttpSession session = CC.getSession();
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(SC.USER_INFO);
		Object subject = (String) userInfo.get("kemu");
		if (subject != null) {
			return subject.toString();
		}
		return null;
	}

	public static <T> T getData(Class<T> vo) {
		try {
			T dataResult = vo.newInstance();
			Field[] fields = vo.getDeclaredFields();
			HttpServletRequest request = CC.getRequest();
			for (Field field : fields) {
				ReflectionUtils.makeAccessible(field);
				// String
				// paramValue=ServletActionContext.getRequest().getParameter(field.getName());
				String[] paramValueArray = request.getParameterValues(field.getName());
				if (paramValueArray != null) {
					if (field.getType().equals(String[].class)) {
						ReflectionUtils.setField(field, dataResult, paramValueArray);
					} else if (field.getType().equals(Character[].class)) {
						Character[] data = new Character[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							data[i] = paramValueArray[i].toCharArray()[0];
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Boolean[].class)) {
						boolean[] data = new boolean[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNotBlank(paramValueArray[i])) {
								data[i] = Boolean.parseBoolean(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(boolean[].class)) {
						boolean[] data = new boolean[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNotBlank(paramValueArray[i])) {
								data[i] = Boolean.parseBoolean(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(int[].class)) {
						int[] data = new int[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Integer.parseInt(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(float[].class)) {
						float[] data = new float[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Float.parseFloat(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Date[].class)) {
						Date[] data = new Date[paramValueArray.length];
						SimpleDateFormat sdf;
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.trim(paramValueArray[i]).length() > 10) {
								sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							} else {
								sdf = new SimpleDateFormat("yyyy-MM-dd");
							}
							try {
								data[i] = sdf.parse(paramValueArray[i]);
							} catch (ParseException e) {
								logger.debug(e.toString());
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Integer[].class)) {
						Integer[] data = new Integer[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Integer(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Float[].class)) {
						Float[] data = new Float[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Float(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Byte[].class)) {
						Byte[] data = new Byte[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Byte(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(byte[].class)) {
						byte[] data = new byte[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Byte.parseByte(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Short[].class)) {
						Short[] data = new Short[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Short(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(short[].class)) {
						short[] data = new short[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Short.parseShort(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Long[].class)) {
						Long[] data = new Long[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Long(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(long[].class)) {
						long[] data = new long[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Long.parseLong(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(Double[].class)) {
						Double[] data = new Double[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = new Double(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(double[].class)) {
						double[] data = new double[paramValueArray.length];
						for (int i = 0; i < paramValueArray.length; i++) {
							if (StringUtils.isNumeric(paramValueArray[i])) {
								data[i] = Double.parseDouble(paramValueArray[i]);
							}
						}
						ReflectionUtils.setField(field, dataResult, data);
					} else if (field.getType().equals(String.class)) {
						if (null != paramValueArray[0]) {
							ReflectionUtils.setField(field, dataResult, paramValueArray[0]);
						}
					} else if (field.getType().equals(Character.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							char[] data = paramValueArray[0].toCharArray();
							ReflectionUtils.setField(field, dataResult, data[0]);
						}
					} else if (field.getType().equals(int.class)) {
						if (StringUtils.isNumeric(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Integer.parseInt(paramValueArray[0]));
						}
					} else if (field.getType().equals(Integer.class)) {
						if (StringUtils.isNumeric(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Integer.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(float.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Float.parseFloat(paramValueArray[0]));
						}
					} else if (field.getType().equals(Float.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Float.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(Date.class)) {
						SimpleDateFormat sdf;
						if (StringUtils.trim(paramValueArray[0]).length() > 10) {
							sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						} else {
							sdf = new SimpleDateFormat("yyyy-MM-dd");
						}
						try {
							ReflectionUtils.setField(field, dataResult, sdf.parse(paramValueArray[0]));
						} catch (ParseException e) {
							logger.debug(e.toString());
						}
					} else if (field.getType().equals(Byte.class)) {
						if (StringUtils.isNumeric(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Byte.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(byte.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Byte.parseByte(paramValueArray[0]));
						}
					} else if (field.getType().equals(Short.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Short.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(short.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Short.parseShort(paramValueArray[0]));
						}
					} else if (field.getType().equals(Long.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Long.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(long.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Long.parseLong(paramValueArray[0]));
						}
					} else if (field.getType().equals(Double.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Double.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(double.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Double.parseDouble(paramValueArray[0]));
						}
					} else if (field.getType().equals(Boolean.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Boolean.valueOf(paramValueArray[0]));
						}
					} else if (field.getType().equals(boolean.class)) {
						if (StringUtils.isNotBlank(paramValueArray[0])) {
							ReflectionUtils.setField(field, dataResult, Boolean.parseBoolean(paramValueArray[0]));
						}
					}

				}
			}
			return dataResult;
		} catch (InstantiationException e) {
			logger.debug(e.toString());
		} catch (IllegalAccessException e1) {
			logger.debug(e1.toString());
		}
		return null;
	}

	public static boolean validateData(Object dataObj, ReturnResult returnResult, Class<?> validGroup) {
		if (dataObj == null) {
			returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage("无法获得请求参数，请检查参数格式"));
			return true;
		}
		Set<ConstraintViolation<Object>> constraintViolations;
		if (validGroup != null) {
			constraintViolations = validator.validate(dataObj, validGroup);
		} else {
			constraintViolations = validator.validate(dataObj);
		}
		if (!constraintViolations.isEmpty()) {
			for (ConstraintViolation<Object> cv : constraintViolations) {
				returnResult.setStatus(StatusCode.PARAMETER_ERROR.setMessage(cv.getMessage()));
				return true;
			}
		}
		return false;
	}

	public static boolean validateData(Object dataObj, ReturnResult returnResult) {
		return validateData(dataObj, returnResult, null);
	}

}
