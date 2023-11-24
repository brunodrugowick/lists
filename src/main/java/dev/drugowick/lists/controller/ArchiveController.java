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
@RequestMapping("/archive")
public class ArchiveController extends BaseController {

    private final MyListService listService;

    public ArchiveController(MyListService listService) {
        this.listService = listService;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public String archivedItems(
            @PathVariable("uuid") UUID listUUID, Model model, Principal principal) {
        var list = listService.findByUUID(listUUID, principal.getName());
        model.addAttribute("list", list);
        return "archive";
    }

    @RequestMapping(value = "/{list-uuid}/items/{item-uuid}/restore", method = RequestMethod.POST)
    public String restoreListItem(@PathVariable("list-uuid") UUID listUuid,
                                  @PathVariable("item-uuid") UUID itemUuid,
                                  Model model,
                                  Principal principal) {
        var list = listService.findByUUID(listUuid, principal.getName());
        var modifiedList = listService.restoreItem(list.getId(), itemUuid, principal.getName());
        model.addAttribute("list", modifiedList);
        return "archive";
    }
}
