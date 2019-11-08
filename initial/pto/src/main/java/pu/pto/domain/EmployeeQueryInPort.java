package pu.pto.domain;

public interface EmployeeQueryInPort {
	
	Employee getEmployeeByEmail(String email);
	
	public Employee getEmployee(String username);

	Employee getCurrentActiveEmployee();

}
