package com.siq.iqrestmenu.web;

import com.siq.iqrestmenu.domain.MenuItemList;
import com.siq.iqrestmenu.service.RemoteDataImporterService;
import com.siq.iqrestmenu.service.SearchMenuItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchMenuItemsController {

    @Autowired
    SearchMenuItemsService searchMenuItemsService;

    @GetMapping(path = "${iqrestmenu.search.shortname.url.pattern}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuItemList getmenuItems(@PathVariable String shortName) throws Exception {

             MenuItemList menuItemList = searchMenuItemsService.getMenuItemsByShortName(shortName);
             return menuItemList;
    }

    // If user forgets to type in search string - return full list.   Just a wrapper

    @GetMapping(path = "${iqrestmenu.search.shortname.url.root}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuItemList getmenuItems() throws Exception {

        return this.getmenuItems("");
    }
}


