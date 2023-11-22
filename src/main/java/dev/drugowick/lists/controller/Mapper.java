package dev.drugowick.lists.controller;

import dev.drugowick.lists.controller.dto.ListItemInput;
import dev.drugowick.lists.domain.entity.MyList;
import dev.drugowick.lists.controller.dto.ListInput;
import dev.drugowick.lists.domain.entity.MyListItem;

public class Mapper {
    static MyList toList(ListInput listInput, String username) {
        var myList = new MyList();
        myList.setTitle(listInput.getTitle());
        myList.setDescription(listInput.getDescription());
        myList.setUsername(username);
        return myList;
    }

    static MyListItem toListItem(ListItemInput itemInput, MyList list) {
        var myItem = new MyListItem();
        myItem.setList(list);
        myItem.setDescription(itemInput.getDescription());
        return myItem;
    }
}
