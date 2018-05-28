package com.siq.iqrestmenu.domain;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.logging.Logger;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest

public class MenuItemAvroTests {


    private String schemaString;
    private AvroMapper itemAvroObjectMapper = new AvroMapper();
    private AvroSchema menuItemSchema;

    private Logger logger = Logger.getLogger("MenuItemAvroTests");

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


    @Before
    public void generateAvroSchema() throws Exception {
        this.menuItemSchema =  this.itemAvroObjectMapper.schemaFor(MenuItem.class);
        this.schemaString= menuItemSchema.getAvroSchema().toString();
        logger.info(String.format("In generateAvroSchema.  Schema String: \n", schemaString));

    }

    @Test
    public void testAvroSerializeDeSerializeMenuItem() throws Exception {

        byte[] serializedAvro = itemAvroObjectMapper.writer(this.menuItemSchema).writeValueAsBytes(toWriteMenuItem);
        MenuItem menuItemAfterDeserialization = itemAvroObjectMapper.readerFor(MenuItem.class).with(menuItemSchema).
            readValue(serializedAvro);

        // On deserialization,  id is null in deserialized MenuItem because of the @GeneratedValue annotation
        // So just check a few random fields (except ID) for equality

        Assert.assertEquals(toWriteMenuItem.getId(), menuItemAfterDeserialization.getId());

        Assert.assertEquals(toWriteMenuItem.getShortName(), menuItemAfterDeserialization.getShortName());
        Assert.assertEquals(toWriteMenuItem.getDescription(), menuItemAfterDeserialization.getDescription());
    }


    
}
