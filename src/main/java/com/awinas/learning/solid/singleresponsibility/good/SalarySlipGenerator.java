package com.awinas.learning.solid.singleresponsibility.good;
class SalarySlipGenerator {
    public void generateSalarySlip(Employee employee) {
        System.out.println("Generating salary slip for: " + employee.getName());
    }
}
