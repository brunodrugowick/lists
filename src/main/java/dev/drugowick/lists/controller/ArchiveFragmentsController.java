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
@RequestMapping("/fragments/archive")
public class ArchiveFragmentsController {

    private final MyListService listService;

    public ArchiveFragmentsController(MyListService listService) {
        this.listService = listService;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public String getArchivedListItems(@PathVariable("uuid") UUID uuid, Model model, Principal principal) {
        model.addAttribute("list", listService.findByUUID(uuid, principal.getName()));
        return "fragments/list-items-archived";
    }

}
