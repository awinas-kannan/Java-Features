package com.awinas.learning.solid.singleresponsibility.good;

/*
 * 
 * 
 
Why is this a Good Design?
✅ Single Responsibility: Each class has a clear purpose and only one reason to change.
✅ Better Maintainability: If salary slip generation logic changes, we update only SalarySlipGenerator, not Employee.
✅ Scalability: If we introduce a new data storage method (e.g., NoSQL, Cloud DB), we modify only EmployeeRepository.


 
 */
public class Main {
	public static void main(String[] args) {
		Employee emp = new Employee("John Doe", 50000);

		SalarySlipGenerator slipGenerator = new SalarySlipGenerator();
		slipGenerator.generateSalarySlip(emp);

		EmployeeRepository repository = new EmployeeRepository();
		repository.saveToDatabase(emp);
	}
}
