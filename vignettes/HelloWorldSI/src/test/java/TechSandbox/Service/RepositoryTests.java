package TechSandbox.Service;

import TechSandbox.HelloWorldSiApplicationTests;
import TechSandbox.Service.DepartmentRepository;
import TechSandbox.Service.EmployeeRepository;
import TechSandbox.domain.Department;
import TechSandbox.domain.Employee;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by ashok.natesan on 12/24/16.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTests {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private static Logger logger = LoggerFactory.getLogger(HelloWorldSiApplicationTests.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testImport() {
        // Check to see if 4 departments imported

        Iterable<Department> departmentList = departmentRepository.findAll();
        int count=0;
        for (Department dept: departmentList
                ) {count++;
        }
        Assert.assertEquals(count, 4);

        // Check to see if 7 employees imported

        Iterable<Employee> employeeList = employeeRepository.findAll();
        count=0;
        for (Employee emp: employeeList
                ) {count++;
        }
        Assert.assertEquals(count, 7);


    }

    @Test
    public void testSaveDelete() {
        // Add a couple more departments

        Department d1 = departmentRepository.save(Department.builder().city("Cupertino").name("SupplyChain").build());
        Department d2= departmentRepository.save(Department.builder().city("Cupertino").name("Procurement").build());


        Iterable<Department> departmentList = departmentRepository.findAll();
        int count = 0;
        for (Department d: departmentList) {count++;};
        Assert.assertEquals(count, 6);

        departmentRepository.delete(d1);
        departmentRepository.delete(d2);

        departmentList = departmentRepository.findAll();
        count = 0;
        for (Department d: departmentList) {count++;};
        Assert.assertEquals(count, 4);

    }

    @Test
    public void shouldCreateNewEmployeeWithExistingDepartmentReference() {
        Department d1 = departmentRepository.findOne(1L);

        Employee e1=Employee.builder().name("Jeremy King").
                userid("jking@gmail.com").
                department(d1).
                build();

        Employee e2 = employeeRepository.save(e1);
        Assert.assertEquals(e2.getUserid(), e1.getUserid());
        employeeRepository.delete(e2);
    }

    @Test
    public void testDuplicateEmployeeUserIdsExpectedException() {

        Department d1 = departmentRepository.findOne(new Long(1));

        // "ppatel" is a duplicate id to check for constraint violation

        Employee e1 = Employee.builder().userid("ppatel").
                name("fooName").department(d1).build();

        // exception expected on save,  not on builder above.  Position of statement below
        // here tests that.
        thrown.expect(DataIntegrityViolationException.class);
        employeeRepository.save(e1);
    }

}
