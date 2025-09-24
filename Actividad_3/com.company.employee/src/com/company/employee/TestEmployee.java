package com.company.employee;

public class TestEmployee {
    public static void main(String[] args) {
        Employee emp1 = new Employee(1, "Carlos", "Burbano", 2500);

        System.out.println(emp1);  // Llama a toString()
        System.out.println("ID: " + emp1.getID());
        System.out.println("Name: " + emp1.getName());
        System.out.println("Annual Salary: " + emp1.getAnnualSalary());

        emp1.raiseSalary(10);
        System.out.println("New salary after 10% raise: " + emp1.getSalary());
    }
}
