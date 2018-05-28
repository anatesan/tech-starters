package TechSandbox.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by ashok.natesan on 12/16/16.
 */
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Data
public class Department {
    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String name;

    @NonNull
    private String city;

    @Override
    public String toString() {
        return getId() + ": " + getName() + ", " + getCity();
    }

}

