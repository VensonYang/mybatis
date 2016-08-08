package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import model.User;
import service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("helloworld")
	public void helloworld(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().println(
				"this my frist spring mvc project.<br>welcome to javacoder world.<br>In here,you can bulid world by youself!");
	}

	@RequestMapping("addUser")
	public void addUser(User user, HttpServletResponse response) {
		userService.addUser(user);
		print(response, "addUser success");
		showList(response, userService.showList());
	}

	@RequestMapping("getUser")
	public void getUser(HttpServletResponse response) {
		print(response, userService.get(1).toString());
	}

	@RequestMapping("updateUser")
	public void updateUser(User user, HttpServletResponse response) {
		userService.updateUser(user);
		print(response, "updateUser success");
		showList(response, userService.showList());
	}

	@RequestMapping("deleteUser")
	public void deleteUser(Integer id, HttpServletResponse response) {
		userService.deleteUser(id);
		print(response, "deleteUser success");
		showList(response, userService.showList());

	}

	private void print(HttpServletResponse response, String message) {
		try {
			response.getWriter().println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showList(HttpServletResponse response, List<User> list) {
		try {
			PrintWriter out = response.getWriter();
			for (User user : list) {
				out.println(user.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
}
