package com.siq.iqrestmenu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.avro.reflect.AvroSchema;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by ashok.natesan on 7/21/17.
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("short_name")
    private String shortName;

    private String name;
    private String description;

    @JsonProperty("price_small")
    private Float priceSmall;  // TODO: Currency designation for persistence?

    @JsonProperty("price_large")
    private Float priceLarge;  // TODO: Currency designation for persistence?

    @JsonProperty("small_portion_name")
    private String smallPortionName;

    @JsonProperty("large_portion_name")
    private String largePortionName;

}
