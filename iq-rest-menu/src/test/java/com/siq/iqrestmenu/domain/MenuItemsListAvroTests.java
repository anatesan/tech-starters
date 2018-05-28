package com.siq.iqrestmenu.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.google.common.collect.Iterables;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;

@RunWith(SpringRunner.class)
@JsonTest

public class MenuItemsListAvroTests {


    private String schemaString;
    private AvroMapper menuItemListAvroMapper = new AvroMapper();

    private ObjectMapper menuItemListObjectMapper = new ObjectMapper()
            .configure(ALLOW_UNQUOTED_FIELD_NAMES, true);

    private AvroSchema menuItemListSchema;
    private MenuItemList menuItemList;

    private Logger logger = Logger.getLogger(this.getClass().getName());




    @Before
    public void generateAvroSchema() throws Exception {
        this.menuItemListSchema =  this.menuItemListAvroMapper.schemaFor(MenuItemList.class);
        this.schemaString= menuItemListSchema.getAvroSchema().toString();
        logger.info(String.format("In generateAvroSchema. MenuItemList Schema String: %s\n", schemaString));

    }

    @Test
    public void testAvroSerializeDeSerializeMenuItemlist() throws Exception {

        // First deserialize a MenuList Item from the JSON Array used in other tests.

        URL jsonFileResource = MenuItemsListAvroTests.class.getResource("testMenuItemArrayForReadUnquotedFieldNames.json");
        File jsonFile = Paths.get(jsonFileResource.toURI()).toFile();
        this.menuItemList =  this.menuItemListObjectMapper.readValue(jsonFile, MenuItemList.class);

        // Serialize to AVRO

        byte[] serializedAvro = menuItemListAvroMapper.writer(this.menuItemListSchema).writeValueAsBytes(this.menuItemList);

        // Deserialize from AVRO

        MenuItemList menuItemListAfterDeserialization = menuItemListAvroMapper.readerFor(MenuItemList.class).with(menuItemListSchema).
            readValue(serializedAvro);

        // On deserialization,  id is null in deserialized MenuItem because of the @GeneratedValue annotation
        // So just check a few random fields (except ID) for equality

        // Check for list size equality
        Assert.assertEquals(Iterables.size(this.menuItemList.getMenuItems()),
                Iterables.size(menuItemListAfterDeserialization.getMenuItems()));

        for (int i = 0; i < 0; Iterables.size(this.menuItemList.getMenuItems())) {
            MenuItem expectedMenuItem = Iterables.get(this.menuItemList.getMenuItems(), i);
            MenuItem actualMenuItem = Iterables.get(menuItemListAfterDeserialization.getMenuItems(), i);

            //Validate a few fields

            Assert.assertEquals(expectedMenuItem.getDescription(), actualMenuItem.getDescription());
            Assert.assertEquals(expectedMenuItem.getShortName(), actualMenuItem.getShortName());

        }




    }


    
}
