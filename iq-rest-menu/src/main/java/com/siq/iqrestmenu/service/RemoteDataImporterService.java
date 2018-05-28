package com.siq.iqrestmenu.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siq.iqrestmenu.domain.MenuItemList;
import com.siq.iqrestmenu.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

/**
 * Created by ashok.natesan on 7/21/17.
 *
 * Reads menu_items from external REST service and populates the local DB.
 */

@Service
public class RemoteDataImporterService {

    private RemoteDataImporterServiceProperties remoteDataImporterServiceProperties;

    private Logger logger = Logger.getLogger("RemoteDataImporterService");

    private RestTemplate restTemplate;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    public RemoteDataImporterService(RemoteDataImporterServiceProperties remoteDataImporterServiceProperties) {
        this.remoteDataImporterServiceProperties = remoteDataImporterServiceProperties;
        this.restTemplate = new RestTemplate();
    }

    public RemoteDataImporterServiceProperties getRemoteDataImporterServiceProperties() {
        return remoteDataImporterServiceProperties;
    }

    // Don't bother wrapping exceptions - complicates code unnecessarily

    public  MenuItemList getMenuItems() throws Exception {

        // Easier to use Object Mapper to deserialize since we need to accept unquoted strings.

        ResponseEntity<String> responseEntity = this.restTemplate
                .getForEntity(remoteDataImporterServiceProperties.getRemoteRestaurantServiceMenuItemsURL(), String.class);

        // Deserialize if rest call succeeded

        ObjectMapper objectMapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        MenuItemList menuItemList = objectMapper.readValue(responseEntity.getBody(), MenuItemList.class);

        return menuItemList;

    }

    // easier to test with a real return value - record count
    public long saveMenuItems(MenuItemList menuItemList) {
        menuItemRepository.save(menuItemList.getMenuItems());
        return getRepoCountHelper();
    }


    public long getRepoCountHelper() {
        return menuItemRepository.count();
    }

    // For usage in tests only
    protected void cleanRepoHelper() {
        menuItemRepository.deleteAll();
    }

    //  This is being exposed for the test suites only
    protected RestTemplate getRestTemplateHelper() {
        return restTemplate;
    }
}

