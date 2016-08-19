package mybatis.generator;
/*
 *  Copyright 2008 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

public class GeneratorComment implements CommentGenerator {

	private Properties properties;
	private boolean suppressAllComments;
	private static final char SEPARATOR = '_';

	public GeneratorComment() {
		super();
		properties = new Properties();
		suppressAllComments = false;
	}

	public void addJavaFileComment(CompilationUnit compilationUnit) {
		return;
	}

	/**
	 * xml中的注释
	 *
	 * @param xmlElement
	 */
	public void addComment(XmlElement xmlElement) {
		if (suppressAllComments) {
			return;
		}
		// xmlElement.addElement(new TextElement("<!--"));
		// StringBuilder sb = new StringBuilder();
		// sb.append(" WARNING - ");
		// xmlElement.addElement(new TextElement(sb.toString()));
		// xmlElement.addElement(new TextElement("-->"));
	}

	public void addRootComment(XmlElement rootElement) {
		return;
	}

	public void addConfigurationProperties(Properties properties) {
		this.properties.putAll(properties);
		suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
	}

	/**
	 * 删除标记
	 *
	 * @param javaElement
	 * @param markAsDoNotDelete
	 */
	protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
		StringBuilder sb = new StringBuilder();
		sb.append(" * ");
		sb.append(MergeConstants.NEW_ELEMENT_TAG);
		if (markAsDoNotDelete) {
			sb.append(" do_not_delete_during_merge");
		}
		javaElement.addJavaDocLine(sb.toString());
	}

	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}
	}

	/**
	 * 给字段添加数据库备注
	 *
	 * @param field
	 * @param introspectedTable
	 * @param introspectedColumn
	 */
	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}
		String columnName = introspectedColumn.getActualColumnName();
		if (!MapperPlugin.commField.contains(toCamelCase(columnName))) {
			String remark = introspectedColumn.getRemarks();
			String message = (StringUtility.stringHasValue(remark) ? remark : columnName);
			if (introspectedColumn.isIdentity()) {
				if (introspectedColumn.isStringColumn()) {
					field.addAnnotation("@NotBlank(message = \"id不能为空\", groups = { IModifyModel.class })");
				} else {
					field.addAnnotation("@NotNull(message = \"id不能为空\", groups = { IModifyModel.class })");
				}
			} else {
				// 判断是否允许为空
				if (!introspectedColumn.isNullable()) {
					// 判读是否为字符串
					if (introspectedColumn.isStringColumn()) {
						field.addAnnotation("@NotBlank(message = \"" + message
								+ "不能为空\", groups = { IModifyModel.class,IAddModel.class })");
						// 判断有无设置长度
						int length = introspectedColumn.getLength();
						if (length < 255) {
							field.addAnnotation("@Length(min=1, max=" + length + ",message=\"" + message + "长度必须介于1-"
									+ length + "之间\", groups = { IModifyModel.class,IAddModel.class })");
						}
					} else {
						field.addAnnotation("@NotNull(message = \"" + message
								+ "不能为空\", groups = { IModifyModel.class,IAddModel.class })");
					}
				}

			}
			StringBuilder sb = new StringBuilder("//");
			if (StringUtility.stringHasValue(remark)) {
				sb.append(remark);
				sb.append(" ");
			}
			sb.append(columnName);
			field.addJavaDocLine(sb.toString());
		}

	}

	/**
	 * getter方法注释
	 *
	 * @param method
	 * @param introspectedTable
	 * @param introspectedColumn
	 */
	public void addGetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}
		// StringBuilder sb = new StringBuilder();
		// method.addJavaDocLine("/**");
		// if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
		// sb.append(" * 获取");
		// sb.append(introspectedColumn.getRemarks());
		// method.addJavaDocLine(sb.toString());
		// method.addJavaDocLine(" *");
		// }
		// sb.setLength(0);
		// sb.append(" * @return ");
		// sb.append(introspectedColumn.getActualColumnName());
		// if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
		// sb.append(" - ");
		// sb.append(introspectedColumn.getRemarks());
		// }
		// method.addJavaDocLine(sb.toString());
		// method.addJavaDocLine(" */");
	}

	/**
	 * setter方法注释
	 *
	 * @param method
	 * @param introspectedTable
	 * @param introspectedColumn
	 */
	public void addSetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}
		// StringBuilder sb = new StringBuilder();
		// method.addJavaDocLine("/**");
		// if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
		// sb.append(" * 设置");
		// sb.append(introspectedColumn.getRemarks());
		// method.addJavaDocLine(sb.toString());
		// method.addJavaDocLine(" *");
		// }
		// Parameter parm = method.getParameters().get(0);
		// sb.setLength(0);
		// sb.append(" * @param ");
		// sb.append(parm.getName());
		// if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
		// sb.append(" ");
		// sb.append(introspectedColumn.getRemarks());
		// }
		// method.addJavaDocLine(sb.toString());
		// method.addJavaDocLine(" */");
	}

	/**
	 * Example使用
	 *
	 * @param innerClass
	 * @param introspectedTable
	 * @param markAsDoNotDelete
	 */
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
		if (suppressAllComments) {
			return;
		}
		innerClass.addJavaDocLine(
				"/** * " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + "表* @author venson* */");
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

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub

	}

}
