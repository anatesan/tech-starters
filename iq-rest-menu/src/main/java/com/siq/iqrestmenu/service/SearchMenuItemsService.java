package com.siq.iqrestmenu.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siq.iqrestmenu.domain.MenuItem;
import com.siq.iqrestmenu.domain.MenuItemList;
import com.siq.iqrestmenu.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by ashok.natesan on 7/21/17.
 *
 * Reads menu_items from external REST service and populates the local DB.
 */

@Service
public class SearchMenuItemsService {

    @Autowired
    private MenuItemRepository menuItemRepository;


    public MenuItemList getMenuItemsByShortName(String shortName) throws Exception {

        Iterable<MenuItem> itemList;

        if (shortName.isEmpty() || shortName == null) {
            itemList = menuItemRepository.findAll();
        }
        else {
            itemList = menuItemRepository.findByShortNameContainingAllIgnoringCase(shortName);
        }
        MenuItemList menuItemList = new MenuItemList();
        menuItemList.setMenuItems(itemList);

        return menuItemList;
    }


}





