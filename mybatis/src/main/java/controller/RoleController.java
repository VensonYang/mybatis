package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.base.BaseController;
import dao.model.TRole;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController<TRole> {

}
