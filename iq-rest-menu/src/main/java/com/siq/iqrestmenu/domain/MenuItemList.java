package com.siq.iqrestmenu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


// Helper Method for Deserializing arrays.  Not persisted
@Data

public class MenuItemList {

    @Getter
    @Setter
    @JsonProperty(value="menu_items")
    Iterable<MenuItem> menuItems;
}
