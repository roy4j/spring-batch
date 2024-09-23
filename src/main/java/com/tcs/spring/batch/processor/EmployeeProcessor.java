package com.tcs.spring.batch.processor;

import com.tcs.spring.batch.domain.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee employee) {
        employee.setFirstName(employee.getFirstName().toUpperCase());
        employee.setLastName(employee.getLastName().toUpperCase());
        return employee;
    }

}