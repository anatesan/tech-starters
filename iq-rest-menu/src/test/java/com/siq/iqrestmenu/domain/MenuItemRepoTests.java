package com.siq.iqrestmenu.domain;

import com.google.common.collect.Iterables;
import com.siq.iqrestmenu.domain.MenuItem;
import com.siq.iqrestmenu.domain.MenuItemList;
import com.siq.iqrestmenu.service.RemoteDataImporterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(true)
public class MenuItemRepoTests {

    private final Logger logger = Logger.getLogger("MenuItemRepoTests");

    @Autowired
    private MenuItemRepository menuItemRepository;

    private final MenuItem menuItem1 = MenuItem.builder()
            .id((long) 901)
            .description("item 1")
            .largePortionName("Large Plate")
            .smallPortionName("Small plate")
            .priceLarge((float) 10.25)
            .priceSmall((float) 7.35)
            .shortName("A901")
            .build();
    private final MenuItem menuItem2 = MenuItem.builder()
            .id((long) 902)
            .description("item 2")
            .largePortionName("Large Plate")
            .smallPortionName("Small plate")
            .priceLarge((float) 10.25)
            .priceSmall((float) 7.35)
            .shortName("A902")
            .build();
    private final MenuItem menuItem3 = MenuItem.builder()
            .id((long) 903)
            .description("item 3")
            .largePortionName("Large Plate")
            .smallPortionName("Small plate")
            .priceLarge((float) 6.75)
            .priceSmall((float) 3.35)
            .shortName("C903")
            .build();
    private final MenuItem menuItem4 = MenuItem.builder()
            .id((long) 904)
            .description("item 4")
            .largePortionName("Large Plate")
            .smallPortionName("Small plate")
            .priceLarge((float) 6.75)
            .priceSmall((float) 3.35)
            .shortName("B904")
            .build();


    @Before
    public void setup() {
        menuItemRepository.deleteAll();
        logger.info(String.format("in Before test: count: %d", menuItemRepository.count()));
    }

    // Do not rely upon IDs - verify existence by finding on short_name
    @Test
    public void testSaveSingleMenuItem (){
        List<MenuItem> itemList = new ArrayList();
        itemList.add(menuItem1);
        MenuItemList menuItemList = new MenuItemList();
        menuItemList.setMenuItems(itemList);

        long startCount = menuItemRepository.count();

        logger.info(String.format("Before save at testSaveSingleMenuItem: RepoCount: %d", startCount));

        menuItemRepository.save(menuItemList.getMenuItems());
        long finishCount = menuItemRepository.count();
        logger.info(String.format("At end of testSaveSingleMenuItem: RepoCount: %d", finishCount));

        Assert.isTrue(Iterables.size(menuItemRepository.findByShortName(menuItem1.getShortName()))==1, 
                String.format("Not found menu item with shortName: %s",menuItem1.getShortName()));
        
        // Should not find any other entities

        Assert.isTrue(Iterables.size(menuItemRepository.findByShortName(menuItem2.getShortName()))==0,
                String.format("Unexpected menu item with shortName: %s",menuItem2.getShortName()));
        Assert.isTrue(Iterables.size(menuItemRepository.findByShortName(menuItem3.getShortName()))==0,
                String.format("Unexpected menu item with shortName: %s",menuItem3.getShortName()));
        Assert.isTrue(Iterables.size(menuItemRepository.findByShortName(menuItem4.getShortName()))==0,
                String.format("Unexpected menu item with shortName: %s",menuItem3.getShortName()));




        Assert.isTrue(finishCount-startCount == 1,
                String.format("Single item save failed: Count inserted: %d",
                        finishCount-startCount));


    }

    @Test
    public void testSaveMultipleMenuItems (){
        List<MenuItem> itemList = new ArrayList();
        itemList.add(menuItem1);
        itemList.add(menuItem2);
        itemList.add(menuItem3);
        itemList.add(menuItem4);

        MenuItemList menuItemList = new MenuItemList();
        menuItemList.setMenuItems(itemList);

        long startCount = menuItemRepository.count();

        logger.info(String.format("Before save at testSaveMultipleMenuItems: RepoCount: %d", startCount));

        menuItemRepository.save(menuItemList.getMenuItems());

        long finishCount = menuItemRepository.count();
        logger.info(String.format("At end of testSaveMultipleMenuItems: RepoCount: %d", finishCount));

        Assert.isTrue(Iterables.size(menuItemRepository.findByShortName(menuItem1.getShortName()))==1,
                String.format("Not found menu item with shortName: %s",menuItem1.getShortName()));
        Assert.isTrue(Iterables.size(menuItemRepository.findByShortName(menuItem2.getShortName()))==1,
                String.format("Not found menu item with shortName: %s",menuItem2.getShortName()));
        Assert.isTrue(Iterables.size(menuItemRepository.findByShortName(menuItem3.getShortName()))==1,
                String.format("Not found menu item with shortName: %s",menuItem3.getShortName()));
        Assert.isTrue(Iterables.size(menuItemRepository.findByShortName(menuItem1.getShortName()))==1,
                String.format("Not found menu item with shortName: %s",menuItem4.getShortName()));


        Assert.isTrue(finishCount-startCount == 4,
                String.format("multiple item save failed: Count inserted: %d",
                finishCount-startCount));

    }

}
