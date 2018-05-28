package vignette.jpalombok.repository;

import org.springframework.data.repository.CrudRepository;
import vignette.jpalombok.domain.Department;

/**
 * Created by ashok.natesan on 12/22/16.
 */
public interface JobRepository extends CrudRepository<Department, Long> {
}
