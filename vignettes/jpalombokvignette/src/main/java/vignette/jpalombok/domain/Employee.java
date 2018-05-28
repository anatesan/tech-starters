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

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue
    private long id;

    String lastName;
    String firstName;

    @ManyToOne
    Department dept;

    @ManyToOne
    Job job;
    Date startDate;
}
