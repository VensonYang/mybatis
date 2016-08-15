package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.model.TUser;
import service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@RequestMapping("addUser")
	public void addUser(TUser user, HttpServletResponse response) {
		userService.addUser(user);
		print(response, "addUser success");
	}

	@RequestMapping("findAll")
	@ResponseBody
	public Object findAll(HttpServletResponse response) {
		return userService.findAll();
	}

	@RequestMapping("getMap")
	@ResponseBody
	public Object getMap(HttpServletResponse response) {
		return userService.get();
	}

	@RequestMapping("count")
	@ResponseBody
	public Object count(HttpServletResponse response) {
		return userService.count();
	}

	@RequestMapping("get")
	@ResponseBody
	public Object get(Integer id, HttpServletResponse response) {
		return userService.get(id);
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

	@RequestMapping("return500")
	public String return500(Model model) {
		model.addAttribute("user", userService.get(1));
		return "500";
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
