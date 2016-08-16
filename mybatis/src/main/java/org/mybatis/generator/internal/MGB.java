package org.mybatis.generator.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MGB {
	static Logger logger = LoggerFactory.getLogger(MGB.class);
	static {
		try {
			PropertyConfigurator.configure(
					new FileInputStream("D:/Java/repository/t-mybatis/mybatis/target/classes/config/log4j.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp
					.parseConfiguration(MGB.class.getResourceAsStream("/config/generatorCofiguration.xml"));
			ShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			Context context = config.getContexts().get(0);
			SqlMapGeneratorConfiguration sqlMap = context.getSqlMapGeneratorConfiguration();
			File file = callback.getDirectory(sqlMap.getTargetProject(), sqlMap.getTargetPackage());
			System.out.println(file.getAbsolutePath());
			myBatisGenerator.generate(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
