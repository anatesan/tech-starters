package vignette.jpalombok.domain;

/**
 * Created by ashok.natesan on 12/16/16.
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import lombok.Data;

public class Department {
    @Id
    @GeneratedValue
    private long id;

    private String deptName;
    private String city;

}
