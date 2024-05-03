package org.iesvdm.employee;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test doubles that are "fakes" must be tested
 *
 *
 */
public class EmployeeInMemoryRepositoryTest {

	private EmployeeInMemoryRepository employeeRepository;

	private List<Employee> employees;

	@BeforeEach
	public void setup() {
		employees = new ArrayList<>();
		employeeRepository = new EmployeeInMemoryRepository(employees);
	}

	/**
	 * Descripcion del test:
	 * crea 2 Employee diferentes
	 * aniadelos a la coleccion de employees
	 * comprueba que cuando llamas a employeeRepository.findAll
	 * obtienes los empleados aniadidos en el paso anterior
	 */
	@Test
	public void testEmployeeRepositoryFindAll() {
		Employee e1 = new Employee("Ivan", 1000);
		Employee e2 = new Employee("Manuel", 2000);

		employees.add(e1);
		employees.add(e2);

		List<Employee> empleados = employeeRepository.findAll();

		assertThat(empleados).containsExactly(e1, e2);
	}

	/**
	 * Descripcion del test:
	 * salva un Employee mediante el metodo
	 * employeeRepository.save y comprueba que la coleccion
	 * employees contiene solo ese Employee
	 */
	@Test
	public void testEmployeeRepositorySaveNewEmployee() {
		Employee e = new Employee("Ivan", 1000);

		employeeRepository.save(e);

		assertThat(employees).containsExactlyInAnyOrder(e);
	}

	/**
	 * Descripcion del tets:
	 * crea un par de Employee diferentes
	 * aniadelos a la coleccion de employees.
	 * A continuacion, mediante employeeRepository.save
	 * salva los Employee anteriores (mismo id) con cambios
	 * en el salario y comprueba que la coleccion employees
	 * los contiene actualizados.
	 */
	@Test
	public void testEmployeeRepositorySaveExistingEmployee() {
		Employee e1 = new Employee("Ivan", 1000);
		Employee e2 = new Employee("Manuel", 2000);

		employees.add(e1);
		employees.add(e2);

		e1.setSalary(2000);

		employeeRepository.save(e1);

		assertThat(employees).containsExactlyInAnyOrder(e1, e2);
		assertThat(e1.getSalary()).isEqualTo(2000);
	}
}
