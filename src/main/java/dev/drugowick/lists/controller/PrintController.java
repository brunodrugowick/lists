package dev.drugowick.lists.controller;

import dev.drugowick.lists.service.MyListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/print")
public class PrintController {

    private final MyListService listService;

    public PrintController(MyListService listService) {
        this.listService = listService;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public String printList(
            @PathVariable("uuid") UUID listUUID, Model model, Principal principal) {
        var list = listService.findByUUID(listUUID, principal.getName());
        model.addAttribute("list", list);
        return "print";
    }
}
