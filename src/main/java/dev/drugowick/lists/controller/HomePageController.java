package dev.drugowick.lists.controller;

import dev.drugowick.lists.domain.entity.MyList;
import dev.drugowick.lists.controller.dto.ListInput;
import dev.drugowick.lists.service.MyListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomePageController extends BaseController {

    private final MyListService myListService;

    public HomePageController(MyListService myListService) {
        this.myListService = myListService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("list", new MyList());
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String saveNewList(Model model,
                              @ModelAttribute("list") @Valid ListInput newList,
                              BindingResult bindingResult,
                              Principal principal) {
        if (bindingResult.hasErrors()) return "index";

        myListService.add(Mapper.toList(newList, principal.getName()));
        model.addAttribute("list", new MyList());
        model.addAttribute("errors", null);
        return "index";
    }
}
