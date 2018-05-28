package TechSandbox.Service;

import TechSandbox.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by ashok.natesan on 12/23/16.
 */
@RepositoryRestResource(collectionResourceRel="departments", path = "departments")
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {
    Page<Department> findByCity(@Param("city") String city, Pageable pageable);
    Department findByName(@Param("name") String name);
}
