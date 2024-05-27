package org.iesvdm.employee;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
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

		//creamos empleado1 y lo metemos en la lista employee creada en el beforeEach
		Employee employee1 = new Employee("numero1", 1763.32);
		employees.add(employee1);

		//creamos empleado2 y lo metemos en la lista employee creada en el beforeEach
		Employee employee2 = new Employee("numero2", 2300);
		employees.add(employee2);

		//implementamos el metodo de employeeRepository
		List<Employee> aux = employeeRepository.findAll();
		//comprobamos si la lista devuelta por el metodo findAll contiene al empleado 1 y 2
		assertThat(aux.contains(employee1)).isTrue();
		assertThat(aux.contains(employee2)).isTrue();

	}

	/**
	 * Descripcion del test:
	 * salva un Employee mediante el metodo
	 * employeeRepository.save y comprueba que la coleccion
	 * employees contiene solo ese Employee
	 */
	@Test
	public void testEmployeeRepositorySaveNewEmployee() {

		//creamos el empleado3 y lo guardamos con el metodo save en el objeto ya instanciado
		Employee employee3 = new Employee("numero3", 2300);
		employeeRepository.save(employee3);

		//comprobamos que el objeto contiene el empleado3
		assertThat(employeeRepository.findAll()).containsOnly(employee3);

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
		//creamos empleado1
		Employee employee1 = new Employee("numero1", 1763.32);
		employees.add(employee1);

		//creamos empleado2
		Employee employee2 = new Employee("numero2", 2300);

		//cambiamos el sueldo de los empleados
		//lo guardamos en employeeRepository mediante el metodo propio save
		employee1.setSalary(2400.00);
		employee2.setSalary(2430.65);
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);

		//comprobamos que en la coleccion tambien se han cambiado
		assertThat(employees.get(0).getSalary()).isEqualTo(2400.00);
		assertThat(employees.get(1).getSalary()).isEqualTo(2430.65);

	}
}
