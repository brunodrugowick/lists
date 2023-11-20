package dev.drugowick.lists.controller;

import dev.drugowick.lists.service.MyListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomePageController extends BaseController {

    @RequestMapping("/")
    public String homePage() {
        return "index";
    }
}
