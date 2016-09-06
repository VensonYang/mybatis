package test;

import java.util.List;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import dao.model.TPrivileges;
import dao.model.TRole;
import dao.model.TUser;
import model.system.LoginVO;
import model.system.NodeVO;
import model.system.PrivilegesVectorVO;
import mybatis.generator.MGB;
import service.base.CRUDService;
import service.system.DepartmentService;
import service.system.EnumService;
import service.system.PrivilegesService;
import service.system.RoleService;
import service.system.UserService;

public class TestUserService {

	static Logger logger = LoggerFactory.getLogger(TestUserService.class);
	static {
		PropertyConfigurator.configure(MGB.class.getResourceAsStream("/config/log4j.properties"));
	}
	private UserService service;
	private CRUDService crud;
	private PrivilegesService privildege;
	private DepartmentService dept;
	private RoleService role;
	private EnumService enumService;

	@Before
	public void init() {
		ApplicationContext aCtx = new FileSystemXmlApplicationContext("classpath:config/applicationContext.xml");
		service = (UserService) aCtx.getBean("userService");
		crud = (CRUDService) aCtx.getBean("crudService");
		privildege = (PrivilegesService) aCtx.getBean("privilegesService");
		dept = (DepartmentService) aCtx.getBean("departmentService");
		enumService = (EnumService) aCtx.getBean("enumService");
		role = (RoleService) aCtx.getBean("roleService");

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

	@Test
	public void testgetUserRoleByUserId() {
		List<Map<String, Object>> result = service.getUserRoleByUserId(1);
		logger.debug("{}success", result);
		// 创建一个帐户
	}

	@Test
	public void testgetPrivilegesVectors() {
		Map<Integer, PrivilegesVectorVO> result = service.getPrivilegesVectors(1);
		logger.debug("{}success", result);
		// 创建一个帐户
	}

	@Test
	public void testgetMenuByUserId() {
		NodeVO result = service.getMenuByUserId(1);
		logger.debug("{}success", result);
		// 创建一个帐户
	}

	@Test
	public void testgetPrivilegesByRoleId() {
		List<TPrivileges> result = privildege.getPrivilegesByRoleId(1);
		logger.debug("{}success", result);
		// 创建一个帐户
	}

	@Test
	public void testgetMenu() {
		NodeVO result = privildege.getMenu();
		logger.debug("{}success", result);
		// 创建一个帐户
	}

	@Test
	public void testgetUser() {
		TUser result = service.getUser(1);
		logger.debug("{}success", result);
		// 创建一个帐户
	}

	@Test
	public void testDeleteUser() {
		crud.delete(TUser.class, 15);
	}

	@Test
	public void testgetAllDepartment() {
		List<Map<String, Object>> result = dept.getAllDepartment();
		logger.debug("{}success", result);
	}

	@Test
	public void testgetEnumByTypeId() {
		List<Map<String, Object>> result = enumService.getEnumByTypeId("0001");
		logger.debug("{}success", result);
	}

	@Test
	public void testgetRoles() {
		List<Map<String, Object>> result = role.findRoles();
		logger.debug("{}success", result);
	}

	@Test
	public void testsaveRolePrivileges() {
		TRole roles = new TRole();
		roles.setId(5);
		roles.setPrivilegesIds(new int[] { 1, 2, 3 });
		role.saveRolePrivileges(roles);
	}

}
