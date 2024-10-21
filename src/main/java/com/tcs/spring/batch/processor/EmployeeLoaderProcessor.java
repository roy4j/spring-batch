package com.tcs.spring.batch.processor;

import com.tcs.spring.batch.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmployeeLoaderProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee employee) {
        log.info("EmployeeLoaderProcessor Processing: {}", employee);

        employee.setFirstName(employee.getFirstName().toUpperCase());
        employee.setLastName(employee.getLastName().toUpperCase());

        return employee;
    }

}