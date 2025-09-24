package com.company.employee;

public class Employee {
    // Atributos privados
    private int id;
    private String firstName;
    private String lastName;
    private int salary;

    // Constructor
    public Employee(int id, String firstName, String lastName, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    // Métodos getters
    public int getID() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public int getSalary() {
        return salary;
    }

    // Setter para salario
    public void setSalary(int salary) {
        this.salary = salary;
    }

    // Calcular salario anual
    public int getAnnualSalary() {
        return salary * 12;
    }

    // Aumentar salario en un porcentaje
    public int raiseSalary(int percent) {
        this.salary += this.salary * percent / 100;
        return this.salary;
    }

    // toString
    @Override
    public String toString() {
        return "Employee[id=" + id + ", name=" + getName() + ", salary=" + salary + "]";
    }
}
