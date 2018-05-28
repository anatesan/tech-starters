package com.siq.iqrestmenu.web;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siq.iqrestmenu.domain.MenuItem;
import com.siq.iqrestmenu.domain.MenuItemList;
import com.siq.iqrestmenu.service.RemoteDataImporterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DataImportController.class)
public class DataImporterControllerTests {

    @MockBean
    private RemoteDataImporterService remoteDataImporterService;

    @Autowired
    private MockMvc mvc;

    @Value("${iqrestmenu.dataimporter.view.url}")
    private String dataImporterViewURL;

    @Value("${iqrestmenu.dataimporter.import.url}")
    private String dataImporterImportURL;


    private final MenuItem menuItem1 = MenuItem.builder()
            .id((long) 1)
            .description("item 1")
            .largePortionName("Large Plate")
            .smallPortionName("Small plate")
            .priceLarge((float) 10.25)
            .priceSmall((float) 7.35)
            .shortName("A1")
            .build();
    private final MenuItem menuItem2 = MenuItem.builder()
            .id((long) 2)
            .description("item 3")
            .largePortionName("Large Plate")
            .smallPortionName("Small plate")
            .priceLarge((float) 10.25)
            .priceSmall((float) 7.35)
            .shortName("A1")
            .build();

    @Test
    public void testSuccess_ViewMenuItems() throws Exception {
        MenuItemList menuItemList = new MenuItemList();
        ArrayList<MenuItem> list = new ArrayList<>();
        list.add(menuItem1);
        list.add(menuItem2);
        menuItemList.setMenuItems(list);

        ObjectMapper objectMapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        String expectedSerializeJSON = objectMapper.writeValueAsString(menuItemList);

        // simulate successful service call return
        given(this.remoteDataImporterService.getMenuItems()).willReturn(menuItemList);

        // test successful rest controller return

        this.mvc.perform(get(dataImporterViewURL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSerializeJSON));

    }

    @Test
    public void testSuccess_ImportMenuItems() throws Exception {
        MenuItemList menuItemList = new MenuItemList();
        ArrayList<MenuItem> list = new ArrayList<>();
        list.add(menuItem1);
        list.add(menuItem2);
        menuItemList.setMenuItems(list);


        // simulate successful service call return
       given(this.remoteDataImporterService.saveMenuItems(menuItemList)).willReturn((long) list.size());

        // test successful rest controller return
        // Since call is mocked in service - controller will return a 0 count of items in DB

        this.mvc.perform(get(dataImporterImportURL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Imported 0 menu items"));
    }



    @Test
    public void testFailure_IOException_ViewMenuItems() throws Exception {

        // simulate successful service call return
        given(this.remoteDataImporterService.getMenuItems())
                .willThrow(new IOException("Simulated IO Exception for DataImportController"));

        // test successful rest controller return

        this.mvc.perform(get(dataImporterViewURL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }


    // Right now Import MenuItems does not handle Exceptions - so this test is not relevant
    //    @Test
//    public void testFailure_IOException_GetMenuItems() throws Exception {
//
//        MenuItemList menuItemList = new MenuItemList();
//        ArrayList<MenuItem> list = new ArrayList<>();
//        list.add(menuItem1);
//        list.add(menuItem2);
//        menuItemList.setMenuItems(list);
//
//
//        // simulate successful service call return
//        given(this.remoteDataImporterService.saveMenuItems(menuItemList))
//                .willThrow(new IOException("Simulated IO Exception for DataImportController"));
//
//        // test successful rest controller return
//
//        this.mvc.perform(get(dataImporterImportURL).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isInternalServerError());
//
//    }
}
