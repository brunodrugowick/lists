package dev.drugowick.lists.controller;

import dev.drugowick.lists.domain.entity.MyList;
import dev.drugowick.lists.dto.ListInput;

public class Mapper {
    static MyList toList(ListInput listInput, String username) {
        var myList = new MyList();
        myList.setTitle(listInput.getTitle());
        myList.setDescription(listInput.getDescription());
        myList.setUsername(username);
        return myList;
    }
}
