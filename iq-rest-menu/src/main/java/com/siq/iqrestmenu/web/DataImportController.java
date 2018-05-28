package com.siq.iqrestmenu.web;

import com.siq.iqrestmenu.domain.MenuItemList;
import com.siq.iqrestmenu.service.RemoteDataImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataImportController {

    @Autowired
    RemoteDataImporterService remoteDataImporterService;

    @RequestMapping ("${iqrestmenu.dataimporter.view.url}")
    public MenuItemList getmenuItems() throws Exception {

             MenuItemList menuItemList = remoteDataImporterService.getMenuItems();
             return menuItemList;
    }

    @RequestMapping ("${iqrestmenu.dataimporter.import.url}")
    public String importData() throws Exception {
        MenuItemList menuItemList = remoteDataImporterService.getMenuItems();
        remoteDataImporterService.saveMenuItems(menuItemList);

        long count = remoteDataImporterService.getRepoCountHelper();

        return String.format("Imported %d menu items", count);

    }
}


