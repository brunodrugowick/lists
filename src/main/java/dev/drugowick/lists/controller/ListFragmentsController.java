package dev.drugowick.lists.controller;

import dev.drugowick.lists.domain.entity.MyList;
import dev.drugowick.lists.domain.entity.MyListItem;
import dev.drugowick.lists.service.MyListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/fragments/lists")
public class ListFragmentsController {

    private final MyListService listService;

    public ListFragmentsController(MyListService listService) {
        this.listService = listService;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getNewList(Model model) {
        model.addAttribute("list", new MyList());
        return "fragments/new-list";
    }

    @RequestMapping(value = "{uuid}/items/new", method = RequestMethod.GET)
    public String getNewListItem(@PathVariable("uuid") UUID uuid, Model model, Principal principal) {
        model.addAttribute("list", listService.findByUUID(uuid, principal.getName()));
        model.addAttribute("item", new MyListItem());
        return "fragments/new-list-item";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getListOfLists(Model model, Principal principal) {
        model.addAttribute("lists", listService.findAll(principal.getName()));
        return "fragments/lists-list";
    }

    @RequestMapping(value = "{uuid}/editable-details", method = RequestMethod.GET)
    public String getEditableListDetails(@PathVariable("uuid") UUID uuid, Model model, Principal principal) {
        model.addAttribute("list", listService.findByUUID(uuid, principal.getName()));
        return "fragments/list-details-editable";
    }

    @RequestMapping(value = "{uuid}/items", method = RequestMethod.GET)
    public String getEditableListItems(@PathVariable("uuid") UUID uuid, Model model, Principal principal) {
        model.addAttribute("list", listService.findByUUID(uuid, principal.getName()));
        return "fragments/list-items-editable";
    }

    @RequestMapping(value = "/{list-uuid}/items/{item-uuid}", method = RequestMethod.POST)
    public String updateListItem(@PathVariable("list-uuid") UUID listUuid,
                                 @PathVariable("item-uuid") UUID itemUuid,
                                 @ModelAttribute("item") String itemNewText,
                                 Model model,
                                 Principal principal) {
        var listToUpdate = listService.findByUUID(listUuid, principal.getName());
        var itemToUpdate = listToUpdate.getItems().
                stream().
                filter(i -> itemUuid.equals(i.getId())).
                findAny().orElseThrow();
        if (itemNewText.isBlank()) {
            model.addAttribute("list", listToUpdate);
            model.addAttribute("item", itemToUpdate);
            return "fragments/list-item-editable";
        }

        itemToUpdate.setDescription(itemNewText);
        listService.add(listToUpdate);

        model.addAttribute("list", listToUpdate);
        model.addAttribute("item", itemToUpdate);
        model.addAttribute("toast", true);
        return "fragments/list-item-editable";
    }
}
