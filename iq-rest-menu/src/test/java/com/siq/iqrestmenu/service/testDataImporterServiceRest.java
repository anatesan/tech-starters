package com.siq.iqrestmenu.service;

import com.google.common.collect.Iterables;
import com.siq.iqrestmenu.domain.MenuItemList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

// For reasons I don't quite understand,  cannot seem to Autowire Repository if I use @RestClientTest
// @SpringBootTest does not set up MockServer up correctly either.
// So, best solution seems to be to instantiate the MockServer explicitly in test rather than Autowiring.
@RunWith(SpringRunner.class)
//@RestClientTest({RemoteDataImporterService.class, RemoteDataImporterServiceProperties.class})
@SpringBootTest


public class testDataImporterServiceRest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Autowired
    private RemoteDataImporterService service;

//    @Autowired
    private  MockRestServiceServer server;

    @Autowired
    private ApplicationContext applicationContext;  // to load resources based on absolute, not relative classpath


    @Before
    public  void setup() {
        this.server = MockRestServiceServer.bindTo(service.getRestTemplateHelper()).build();
    }

    @Test
    public void testSuccess_GetMenuItemsListUnquotedJSON()

            throws Exception {

        Resource jsonFileResource = applicationContext.getResource("classpath:com/siq/iqrestmenu/domain/testMenuItemArrayForReadUnquotedFieldNames.json");
        RemoteDataImporterServiceProperties props = service.getRemoteDataImporterServiceProperties();
        this.server.expect(requestTo(props.getRemoteRestaurantServiceMenuItemsURL()))
                .andRespond(withSuccess(jsonFileResource,
                        MediaType.APPLICATION_JSON));
        MenuItemList menuItemList = this.service.getMenuItems();

        assertThat(Iterables.size(menuItemList.getMenuItems()) > 1);

    }

    @Test
    public void testFailure_BadJSON()  //returns successful HttpStatus code but incorrect, even if well formed JSON
            throws Exception {


        Resource jsonFileResource = applicationContext.getResource("classpath:com/siq/iqrestmenu/domain/testMenuItemForRead.json");
        RemoteDataImporterServiceProperties props = service.getRemoteDataImporterServiceProperties();

        //Send back JSON for single menu item, not array - this should fail with an exception


        this.server.expect(requestTo(props.getRemoteRestaurantServiceMenuItemsURL()))
                .andRespond(withSuccess(jsonFileResource,
                        MediaType.APPLICATION_JSON));

        this.thrown.expect(IOException.class);
        MenuItemList menuItemList = this.service.getMenuItems();



    }

    @Test
    public void testFailure_BadHTTPResp() throws Exception  { //Return error status code


        RemoteDataImporterServiceProperties props = service.getRemoteDataImporterServiceProperties();

        //Send back JSON for single menu item, not array - this should fail with RestClientException exception

        this.server.expect(requestTo(props.getRemoteRestaurantServiceMenuItemsURL()))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        this.thrown.expect(HttpClientErrorException.class);
        MenuItemList menuItemList = this.service.getMenuItems();



    }

    @Test
    public void testFailure_ServerError()  //returns server error
            throws Exception {


        RemoteDataImporterServiceProperties props = service.getRemoteDataImporterServiceProperties();

        //Send back JSON for single menu item, not array - this should fail with an exception

        this.server.expect(requestTo(props.getRemoteRestaurantServiceMenuItemsURL()))
                .andRespond(withServerError());

        this.thrown.expect(HttpServerErrorException.class);
        MenuItemList menuItemList = this.service.getMenuItems();



    }


    //  This doesn't seem to work very well - path seems relative to the class invoking it
    //  TODO: Find out how to specify an absolute path and not a relative path
    //  Workaround:  using applicationContext.getResource
    private ClassPathResource getClassPathResource(String path) {
        return new ClassPathResource(path, getClass());
    }


}
