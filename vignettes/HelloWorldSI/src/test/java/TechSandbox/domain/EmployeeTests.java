package TechSandbox.domain;

import TechSandbox.Service.DepartmentRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Created by ashok.natesan on 12/24/16.
 */

public class EmployeeTests {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Rule
    public  ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testEmployeeNullValueValidationExpectedException() {
        expectedException.expect(NullPointerException.class);
        Employee e1 = Employee.builder().name(null).build();
        Employee e2 = Employee.builder().department(null).build();

    }

    @Test
    public void shouldCreateNewEmployee() {
        Department d1 = departmentRepository.findOne(1L);

        Employee e1=Employee.builder().name("Jeremy King").
                userid("jking@gmail.com").
                department(d1).
                build();

    }



}
