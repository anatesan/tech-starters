package com.siq.iqrestmenu.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest

public class MenuItemsListJsonTests {


    private JacksonTester<MenuItemList> jsonList;        //  This should NOT autowired - initFields seems to set this up.
    private ObjectMapper listObjectMapper = new ObjectMapper().configure(ALLOW_UNQUOTED_FIELD_NAMES, true );

    @Before
    public void configureJacksonTester() {

        ObjectMapper listObjectMapper = new ObjectMapper().configure(ALLOW_UNQUOTED_FIELD_NAMES, true );
        JacksonTester.initFields(this, listObjectMapper);
    }

    // Verify that unquoted JSON field names work ok & we can deserialize full arrays
    @Test
    public void testDeserializeMenuItemArrayUnQuotedStrings() {

        try {

          MenuItemList menuItemList = this.jsonList.read("testMenuItemArrayForReadUnquotedFieldNames.json").getObject();
          assertThat(Iterables.size(menuItemList.getMenuItems())>1);

        }
        catch(IOException ioe){
            Assertions.fail(ioe.getMessage());

        }

    }

    @Test
    public void testDeserializeMenuItemArrayUnQuotedStringsUsingObjectMapper() throws Exception {

        URL jsonFileResource = this.getClass().getResource("testMenuItemArrayForReadUnquotedFieldNames.json");
        File jsonFile = Paths.get(jsonFileResource.toURI()).toFile();
        MenuItemList menuItemList =  this.listObjectMapper.readValue(jsonFile, MenuItemList.class);

        Assert.isTrue(menuItemList!=null, "Menu Item List JSON deserialize fails");
        Assert.isTrue(Iterables.size(menuItemList.getMenuItems())>0,
                "Deserialized MenuItemList is empty");

    }


}
