package dev.drugowick.lists.controller;

import dev.drugowick.lists.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminPageController extends BaseController {

    @Autowired
    private final UserService userService;

    public AdminPageController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }
}
