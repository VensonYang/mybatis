package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.base.BaseController;
import dao.model.TUser;

@Controller
@RequestMapping("user")
public class UserController extends BaseController<TUser> {

}
