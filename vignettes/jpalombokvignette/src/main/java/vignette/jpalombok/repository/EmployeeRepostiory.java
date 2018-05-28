package vignette.jpalombok.repository;

import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;

import javax.persistence.Entity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import vignette.jpalombok.domain.Employee;

/**
 * Created by ashok.natesan on 12/19/16.
 */
public interface EmployeeRepostiory extends CrudRepository<Employee, Long> {
    List<Employee> findByLastName(String lastName);
}
