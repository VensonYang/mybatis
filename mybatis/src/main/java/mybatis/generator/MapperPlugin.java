package mybatis.generator;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import dao.BaseDao;
import model.base.BaseModel;
import utils.common.DateFormaterUtil;
import utils.common.MyBeanUtils;

public class MapperPlugin extends PluginAdapter {

	public final static Set<String> commField = new HashSet<>();
	private static final String ID = "id";

	public static final String NOTNULL = NotNull.class.getName();
	public static final String NOTBLANK = NotBlank.class.getName();
	public static final String LENGTH = Length.class.getName();

	static {
		java.lang.reflect.Field[] fields = BaseModel.class.getDeclaredFields();
		for (java.lang.reflect.Field field : fields) {
			commField.add(field.getName());
		}
	}

	public MapperPlugin() {
	}

	public void setContext(Context context) {
		super.setContext(context);

		CommentGeneratorConfiguration commentCfg = new CommentGeneratorConfiguration();
		commentCfg.setConfigurationType(GeneratorComment.class.getCanonicalName());
		context.setCommentGeneratorConfiguration(commentCfg);
	}

	public void setProperties(Properties properties) {
		super.setProperties(properties);
		// 此处可以读取设置的属性
		// String mappers = this.properties.getProperty("mappers");
	}

	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		replaceIDName(element, BaseDao.DELETE);
		return super.sqlMapDeleteByPrimaryKeyElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		replaceIDName(element, BaseDao.SAVE);
		return super.sqlMapInsertElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		replaceIDName(element, BaseDao.GET);
		return super.sqlMapSelectByPrimaryKeyElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		replaceIDName(element, BaseDao.FINDALL);
		return super.sqlMapSelectAllElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		replaceIDName(element, BaseDao.UPDATE);
		return super.sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	private void replaceIDName(XmlElement element, String replcaName) {
		for (Attribute att : element.getAttributes()) {
			if (att.getName().equals(ID)) {
				MyBeanUtils.setFieldValue(att, "value", replcaName);
			}
		}
	}

	private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// 引入BaseModel类
		String baseModel = BaseModel.class.getName();
		Set<FullyQualifiedJavaType> importedTypes = new HashSet<>();
		importedTypes.add(new FullyQualifiedJavaType(baseModel));

		// 获取表名
		String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		if (StringUtility.stringContainsSpace(tableName)) {
			tableName = this.context.getBeginningDelimiter() + tableName + this.context.getEndingDelimiter();
		}
		// 增加注释
		if (!topLevelClass.getType().getShortName().equalsIgnoreCase(tableName))
			topLevelClass.addAnnotation("/**\n * " + tableName + "表\n *\n * @author venson\n *\n * @version "
					+ DateFormaterUtil.dateToString(new Date()) + "\n **/");
		// 查看是否有公有字段,如果存在则移除
		boolean hasCommField = false;
		boolean hasDateField = false;
		Iterator<Field> itf = topLevelClass.getFields().iterator();
		while (itf.hasNext()) {
			Field f = itf.next();
			String name = f.getName();
			if (commField.contains(name)) {
				if (name.equals("createTime") || name.equals("modifyTime")) {
					hasDateField = true;
				}
				itf.remove();
				hasCommField = true;
			} else {
				hasAnnotations(importedTypes, f.getAnnotations());
			}
		}
		if (hasCommField) {
			Iterator<Method> itm = topLevelClass.getMethods().iterator();
			while (itm.hasNext()) {
				Method m = itm.next();
				String name = m.getName();
				name = name.substring(3, name.length());
				name = (name.substring(0, 1).toLowerCase() + name.substring(1, name.length()));
				if (commField.contains(name)) {
					itm.remove();
				}
			}

			topLevelClass.setSuperClass(baseModel);
			// 移除日期引入
			if (hasDateField) {
				@SuppressWarnings("unchecked")
				Set<FullyQualifiedJavaType> imports = (Set<FullyQualifiedJavaType>) MyBeanUtils
						.getFieldValue(topLevelClass, "importedTypes");
				Iterator<FullyQualifiedJavaType> iti = imports.iterator();
				while (iti.hasNext()) {
					FullyQualifiedJavaType type = (FullyQualifiedJavaType) iti.next();
					if (type.getFullyQualifiedName().equals("java.util.Date")) {
						iti.remove();
					}

				}
			}
		}
		// 增加引入包
		topLevelClass.addImportedTypes(importedTypes);

	}

	private void hasAnnotations(Set<FullyQualifiedJavaType> importedTypes, List<String> anns) {
		if (anns != null && anns.size() > 0) {
			for (String ann : anns) {
				if (ann.startsWith(getAnnPro(NOTNULL))) {
					importedTypes.add(new FullyQualifiedJavaType(NOTNULL));
				}
				if (ann.startsWith(getAnnPro(NOTBLANK))) {
					importedTypes.add(new FullyQualifiedJavaType(NOTBLANK));
				}
				if (ann.startsWith(getAnnPro(LENGTH))) {
					importedTypes.add(new FullyQualifiedJavaType(LENGTH));
				}
			}

		}

	}

	private String getAnnPro(String ann) {
		return "@" + ann.substring(ann.lastIndexOf(".") + 1);
	}

	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		processEntityClass(topLevelClass, introspectedTable);
		return true;
	}

}