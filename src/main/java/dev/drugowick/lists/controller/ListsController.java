package dev.drugowick.lists.controller;

import dev.drugowick.lists.domain.entity.MyList;
import dev.drugowick.lists.dto.ListInput;
import dev.drugowick.lists.service.MyListService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/lists")
public class ListsController {

    private final MyListService myListService;
    private final Validator validator;

    public ListsController(MyListService myListService, Validator validator) {
        this.myListService = myListService;
        this.validator = validator;
    }

    @RequestMapping
    public String lists(Model model, Principal principal) {
        model.addAttribute("lists", myListService.findAll(principal.getName()));
        return "fragments/main-list";
    }

    @GetMapping("/new")
    public String newList(Model model) {
        model.addAttribute("list", new ListInput());
        return "fragments/new-list";
    }

    @PostMapping("/new")
    public String saveNewList(Model model, ListInput newList, Principal principal) {
        var violations = validator.validate(newList);
        if (!violations.isEmpty()) {
            // TODO Needs to have validation on client as well, this is temporary
            model.addAttribute("list", newList);
            model.addAttribute("errors", violations);
            return "index";
        }

        myListService.add(toList(newList, principal.getName()));
        return "index";
    }

    private MyList toList(ListInput newList, String username) {
        var myList = new MyList();
        myList.setTitle(newList.getTitle());
        myList.setDescription(newList.getDescription());
        myList.setUsername(username);
        return myList;
    }
}
