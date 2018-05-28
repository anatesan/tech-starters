package vignette.jpalombok.domain;

/**
 * Created by ashok.natesan on 12/16/16.
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity

public class Job {
    @Id
    @GeneratedValue
    int id;

    String jobTitle;
    String jobDescription;
}
