package vignette.jpalombok.repository;

import org.springframework.data.repository.CrudRepository;
import vignette.jpalombok.domain.Department;
import java.util.List;

/**
 * Created by ashok.natesan on 12/22/16.
 */
public interface DeptRepository extends CrudRepository<Department, Long> {
    List<Department> findByDeptName(String deptName);
    List<Department> findByCity(String city);
}
