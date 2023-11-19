package dev.drugowick.lists.controller;

import dev.drugowick.lists.service.MyListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomePageController extends BaseController {

    private final MyListService myListService;

    public HomePageController(MyListService myListService) {
        this.myListService = myListService;
    }

    @RequestMapping("/")
    public String homePage() {
        return "index";
    }

    @RequestMapping("/lists")
    public String lists(Model model, Principal principal) {
        model.addAttribute("lists", myListService.findAll(principal.getName()));
        return "fragments/main-list";
    }
}
