package com.siq.iqrestmenu.web;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siq.iqrestmenu.domain.MenuItem;
import com.siq.iqrestmenu.domain.MenuItemList;
import com.siq.iqrestmenu.service.RemoteDataImporterService;
import com.siq.iqrestmenu.service.SearchMenuItemsService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchMenuItemsController.class)
public class SearchMenuItemsControllerTests {

    @MockBean
    private SearchMenuItemsService searchMenuItemsService;

    @Autowired
    private MockMvc mvc;

    @Value("${iqrestmenu.search.shortname.url.root}")
    private String searchShortNameURLRoot;

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
    public void testSuccess_SearchWithShortName() throws Exception {
        MenuItemList menuItemList = new MenuItemList();
        ArrayList<MenuItem> list = new ArrayList<>();
        list.add(menuItem1);
        list.add(menuItem2);
        menuItemList.setMenuItems(list);

        ObjectMapper objectMapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        String expectedSerializeJSON = objectMapper.writeValueAsString(menuItemList);

        // simulate successful service call return
        given(this.searchMenuItemsService.getMenuItemsByShortName("A1")).willReturn(menuItemList);

        // test successful rest controller return

        this.mvc.perform(get(searchShortNameURLRoot+ "/A1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedSerializeJSON));

    }

    @Test
    public void testFailure_Search_IOException() throws Exception {

        // simulate successful service call return
        given(this.searchMenuItemsService.getMenuItemsByShortName("A1"))
                .willThrow(new IOException("Simulated Random Exception for Search Menu Items Service"));

        // test successful rest controller return

        this.mvc.perform(get(searchShortNameURLRoot + "/A1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }

    // Throw some other random exception to make sure we handle it
    @Test
    public void testFailure_Search_NullPointerException() throws Exception {

        // simulate successful service call return
        given(this.searchMenuItemsService.getMenuItemsByShortName("A1"))
                .willThrow(new NullPointerException("Simulated Null Pointer Exception for Search Menu Items Service"));

        // test successful rest controller return

        this.mvc.perform(get(searchShortNameURLRoot + "/A1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }
}
