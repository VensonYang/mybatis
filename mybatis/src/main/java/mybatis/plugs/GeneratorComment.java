package mybatis.plugs;
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
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

public class GeneratorComment implements CommentGenerator {

	private Properties properties;
	private boolean suppressAllComments;

	public GeneratorComment() {
		super();
		properties = new Properties();
		suppressAllComments = false;
	}

	public void addJavaFileComment(CompilationUnit compilationUnit) {
		compilationUnit.addFileCommentLine("/** @author venson */");
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

	/**
	 * Example使用
	 *
	 * @param innerClass
	 * @param introspectedTable
	 */
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}
		innerClass.addJavaDocLine(
				"/** * " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + "表* @author venson* */");
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
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			field.addJavaDocLine("/**");
			StringBuilder sb = new StringBuilder();
			sb.append(" * ");
			sb.append(introspectedColumn.getRemarks());
			sb.append(" * ");
			sb.append(introspectedColumn.getActualColumnName());
			field.addJavaDocLine(sb.toString());
			field.addJavaDocLine(" */");
		}
	}

	/**
	 * Example使用
	 *
	 * @param field
	 * @param introspectedTable
	 */
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}
	}

	/**
	 * @param method
	 * @param introspectedTable
	 */
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
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
		StringBuilder sb = new StringBuilder();
		method.addJavaDocLine("/**");
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" * 获取");
			sb.append(introspectedColumn.getRemarks());
			method.addJavaDocLine(sb.toString());
			method.addJavaDocLine(" *");
		}
		sb.setLength(0);
		sb.append(" * @return ");
		sb.append(introspectedColumn.getActualColumnName());
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" - ");
			sb.append(introspectedColumn.getRemarks());
		}
		method.addJavaDocLine(sb.toString());
		method.addJavaDocLine(" */");
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
		StringBuilder sb = new StringBuilder();
		method.addJavaDocLine("/**");
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" * 设置");
			sb.append(introspectedColumn.getRemarks());
			method.addJavaDocLine(sb.toString());
			method.addJavaDocLine(" *");
		}
		Parameter parm = method.getParameters().get(0);
		sb.setLength(0);
		sb.append(" * @param ");
		sb.append(parm.getName());
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
			sb.append(" ");
			sb.append(introspectedColumn.getRemarks());
		}
		method.addJavaDocLine(sb.toString());
		method.addJavaDocLine(" */");
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
}
