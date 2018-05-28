package com.siq.iqrestmenu.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest

public class MenuItemJsonTests {


    private JacksonTester<MenuItem> jsonSingleItem;  //  This should NOT autowired - initFields seems to set this up.



    @Before
    public void configureJacksonTester() {
        ObjectMapper itemObjectMapper = new ObjectMapper().configure(ALLOW_UNQUOTED_FIELD_NAMES, true );
        JacksonTester.initFields(this, itemObjectMapper);
    }

    @Test
    public void testSerializeMenuItem() {

        MenuItem toWriteMenuItem = MenuItem.builder().
                id((long)444).
                description("Test menu item").
                shortName("A1").
                name("I444").
                priceLarge((float)33.45).
                priceSmall((float)23.45).
                largePortionName("Big Plate").
                smallPortionName("Small Plate").
                build();
        try {
            this.jsonSingleItem.write(toWriteMenuItem).assertThat().
                    extractingJsonPathStringValue("name").isEqualTo("I444");
            this.jsonSingleItem.write(toWriteMenuItem).assertThat().
                    extractingJsonPathStringValue("large_portion_name").isEqualTo("Big Plate");
        }
        catch(IOException ioe){
            System.out.println(ioe.getStackTrace());

        }

    }

    @Test
    public void testDeserializeMenuItemQuotedStrings() {

        try {

            MenuItem toReadMenuItem = this.jsonSingleItem.read("testMenuItemForRead.json").
                    getObject();
            assertThat(toReadMenuItem.getName()=="Won Ton Soup with Chicken");
            assertThat(toReadMenuItem.getPriceLarge()==5.27);

        }
        catch(IOException ioe){
            Assertions.fail(ioe.getMessage());

        }

    }

    // Verify that unquoted JSON field names work ok
    @Test
    public void testDeserializeMenuItemUnQuotedStrings() {

        try {

            MenuItem toReadMenuItem = this.jsonSingleItem.read("testMenuItemForReadUnquotedFieldNames.json").
                    getObject();
            assertThat(toReadMenuItem.getName()=="Won Ton Soup with Chicken");
            assertThat(toReadMenuItem.getPriceLarge()==5.27);

        }
        catch(IOException ioe){
            Assertions.fail(ioe.getMessage());

        }

    }


}
