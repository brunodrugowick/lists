package dev.drugowick.lists.controller;

import dev.drugowick.lists.controller.dto.ListItemInput;
import dev.drugowick.lists.domain.entity.MyListItem;
import dev.drugowick.lists.controller.dto.ListInput;
import dev.drugowick.lists.service.MyListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/lists")
public class ListPageController extends BaseController{

    private final MyListService listService;

    public ListPageController(MyListService listService) {
        this.listService = listService;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.POST)
    @PostMapping
    public String updateList(
            @PathVariable("uuid") UUID uuid,
            @ModelAttribute("list") @Valid ListInput changedList,
            BindingResult bindingResult,
            Model model,
            Principal principal) {
        var listToUpdate = listService.findByUUID(uuid, principal.getName());
        model.addAttribute("newitem", new MyListItem());

        if (bindingResult.hasErrors()) {
            model.addAttribute("list", listToUpdate);
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return "list";
        }

        listToUpdate.setTitle(changedList.getTitle());
        listToUpdate.setDescription(changedList.getDescription());
        var updatedList = listService.add(listToUpdate);
        model.addAttribute("list", updatedList);
        model.addAttribute("errors", null);
        return "list";
    }


    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public String getList(@PathVariable("uuid") UUID uuid, Model model, Principal principal) {
        var list = listService.findByUUID(uuid, principal.getName());
        model.addAttribute("list", list);
        model.addAttribute("newitem", new MyListItem());
        return "list";
    }

    @RequestMapping(value = "/{list-uuid}/items/{item-uuid}", method = RequestMethod.DELETE)
    public String deleteListItem(@PathVariable("list-uuid") UUID listUuid,
                                 @PathVariable("item-uuid") UUID itemUuid,
                                 Model model,
                                 Principal principal) {
        var list = listService.findByUUID(listUuid, principal.getName());
        list.getItems().removeIf(myListItem -> myListItem.getId().equals(itemUuid));
        listService.deleteItem(listUuid, itemUuid, principal.getName());
        model.addAttribute("list", list);
        model.addAttribute("newitem", new MyListItem());
        return "list";
    }

    @RequestMapping(value = "/{list-uuid}/items", method = RequestMethod.POST)
    public String createListItem(@PathVariable("list-uuid") UUID listUuid,
                                 @ModelAttribute("newitem") @Valid ListItemInput newItem,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal) {
        var listToUpdate = listService.findByUUID(listUuid, principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("list", listToUpdate);
            model.addAttribute("itemerrors", bindingResult.getFieldErrors());
            return "list";
        }

        listToUpdate.getItems().add(Mapper.toListItem(newItem, listToUpdate));
        var updatedList = listService.add(listToUpdate);
        model.addAttribute("list", updatedList);
        model.addAttribute("newitem", new MyListItem());
        model.addAttribute("errors", null);
        return "list";
    }
}
