package com.siq.iqrestmenu.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class RemoteDataImporterServiceProperties {

    @Value("${restaurant.root.url}")
    private String remoteRestaurantServiceURL;

    @Value("${restaurant.menuitems}")
    private String menuItemsCommandString;

    public String getRemoteRestaurantServiceRootURL() {
        return remoteRestaurantServiceURL;
    }

    public String getRemoteRestaurantServiceMenuItemsURL() {
        return remoteRestaurantServiceURL + "/" + menuItemsCommandString;
    }
}
