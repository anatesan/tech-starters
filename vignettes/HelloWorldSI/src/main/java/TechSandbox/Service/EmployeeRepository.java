package TechSandbox.Service;

import TechSandbox.domain.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by ashok.natesan on 12/23/16.
 */
@RepositoryRestResource(collectionResourceRel = "employees", path="employees")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Employee findByName(@Param ("name")  String name);
    Employee findByUserid(@Param("userid") String userid);
    Employee findById(@Param("id") long id);
}
