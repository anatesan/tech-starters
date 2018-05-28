package TechSandbox.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by ashok.natesan on 12/23/16.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    long id;

    @NonNull
    @Column (unique = true)
    private String userid;

    @NonNull
    private String name;

    @ManyToOne
    @NonNull
    Department department;

    @Override
    public String toString() {
        return getId() + ": " + getName() + "@" + getDepartment().getName() + " (" + getDepartment().getCity() + ")";
    }

}
