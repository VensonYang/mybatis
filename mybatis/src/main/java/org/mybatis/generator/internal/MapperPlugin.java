package org.mybatis.generator.internal;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import dao.BaseDao;
import dao.BaseModel;
import utils.common.DateFormaterUtil;

public class MapperPlugin extends PluginAdapter {

	private Set<String> mappers;
	private static Set<String> commField = new HashSet<>();
	private static final char SEPARATOR = '_';
	private static final String ID = "id";

	static {
		java.lang.reflect.Field[] fields = BaseModel.class.getDeclaredFields();
		for (java.lang.reflect.Field field : fields) {
			commField.add(field.getName());
		}
		System.out.println(commField);
	}

	public MapperPlugin() {
		this.mappers = new HashSet<String>();
	}

	public void setContext(Context context) {
		super.setContext(context);

		// CommentGeneratorConfiguration commentCfg = new
		// CommentGeneratorConfiguration();
		// commentCfg.setConfigurationType(MapperCommentGenerator.class.getCanonicalName());
		// context.setCommentGeneratorConfiguration(commentCfg);
	}

	public void setProperties(Properties properties) {
		super.setProperties(properties);
		String mappers = this.properties.getProperty("mappers");
		if (StringUtility.stringHasValue(mappers)) {
			for (String mapper : mappers.split(","))
				this.mappers.add(mapper);
		} else
			System.out.println("Mapper插件缺少必要的mappers属性!");
	}

	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
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

	private void replaceIDName(XmlElement element, String replcaName) {
		List<Attribute> atts = element.getAttributes();
		Iterator<Attribute> it = atts.iterator();
		while (it.hasNext()) {
			Attribute att = it.next();
			if (att.getName().equals(ID)) {
				it.remove();
			}

		}
		Attribute attribute = new Attribute(ID, replcaName);
		element.addAttribute(attribute);
	}

	private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		if (StringUtility.stringContainsSpace(tableName)) {
			tableName = this.context.getBeginningDelimiter() + tableName + this.context.getEndingDelimiter();
		}
		boolean hasCommField = false;
		if (!topLevelClass.getType().getShortName().equalsIgnoreCase(tableName))
			topLevelClass.addAnnotation("/**\n * " + tableName + "表\n *\n * @author venson\n *\n * @Version"
					+ DateFormaterUtil.dateToString(new Date()) + "\n  **/");
		List<Field> fields = topLevelClass.getFields();
		Iterator<Field> itf = fields.iterator();
		while (itf.hasNext()) {
			Field f = itf.next();
			String name = f.getName();
			if (commField.contains(name)) {
				itf.remove();
				hasCommField = true;
			}
		}
		if (hasCommField) {
			List<Method> methods = topLevelClass.getMethods();
			Iterator<Method> itm = methods.iterator();
			while (itm.hasNext()) {
				Method m = itm.next();
				String name = m.getName();
				name = name.substring(3, name.length());
				name = (name.substring(0, 1).toLowerCase() + name.substring(1, name.length()));
				if (commField.contains(name)) {
					itm.remove();
				}
			}
			String baseModel = BaseModel.class.getName();
			topLevelClass.addImportedType(baseModel);
			topLevelClass.setSuperClass(baseModel);
		}

	}

	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		processEntityClass(topLevelClass, introspectedTable);
		return true;
	}

	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}
}