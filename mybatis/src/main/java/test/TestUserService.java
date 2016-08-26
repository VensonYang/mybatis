package test;

import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import model.system.LoginVO;
import mybatis.generator.MGB;
import service.system.UserService;

public class TestUserService {

	static Logger logger = LoggerFactory.getLogger(TestUserService.class);
	static {
		PropertyConfigurator.configure(MGB.class.getResourceAsStream("/config/log4j.properties"));
	}
	private UserService service;

	@Before
	public void init() {
		ApplicationContext aCtx = new FileSystemXmlApplicationContext("classpath:config/applicationContext.xml");
		UserService service = (UserService) aCtx.getBean("userService");
		this.service = service;

	}

	@Test
	public void testGetUserByAccount() {
		Map<String, Object> result = service.getUserByAccount("9999");
		logger.debug("{}", result);
		// 创建一个帐户
	}

	@Test
	public void testgetUserByCode() {
		LoginVO loginVO = new LoginVO();
		loginVO.setPassword("");
		loginVO.setUserAccount("9999");
		Map<String, Object> result = service.getUserByCode(loginVO);
		logger.debug("{}", result);
		// 创建一个帐户
	}

	@Test
	public void testmodifyPassword() {
		service.modifyPassword(1, "123456");
		logger.debug("{}", "success");
		// 创建一个帐户
	}

}
