package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.model.TUser;
import service.UserService;

@Controller
@RequestMapping("user")
@ResponseBody
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@RequestMapping("addUser")
	public void addUser(TUser user, HttpServletResponse response) {
		userService.addUser(user);
		print(response, "addUser success");
	}

	@RequestMapping("getUser")
	public Object getUser(HttpServletResponse response) {
		Map<String, Object> result = userService.getUser();
		logger.debug(result.toString());
		return result;
	}

	@RequestMapping("get")
	public void get(Integer id, HttpServletResponse response) {
		print(response, userService.get(id).toString());
	}

	@RequestMapping("updateUser")
	public void updateUser(TUser user, HttpServletResponse response) {
		userService.updateUser(user);
		print(response, "updateUser success");
	}

	@RequestMapping("deleteUser")
	public void deleteUser(Integer id, HttpServletResponse response) {
		userService.deleteUser(id);
		print(response, "deleteUser success");
	}

	private void print(HttpServletResponse response, String message) {
		try {
			PrintWriter out = response.getWriter();
			out.println(message);
			logger.debug(message);
			for (TUser user : userService.queryUser()) {
				out.println(user.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
