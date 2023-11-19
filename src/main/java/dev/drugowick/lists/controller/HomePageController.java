package dev.drugowick.lists.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController extends BaseController {
    @RequestMapping("/")
    public String homePage() {
        return "index";
    }
}
