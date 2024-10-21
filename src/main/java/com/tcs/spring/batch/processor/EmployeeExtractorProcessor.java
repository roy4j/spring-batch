package com.tcs.spring.batch.processor;

import com.tcs.spring.batch.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Slf4j
@Component
public class EmployeeExtractorProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee employee) {
        log.info("EmployeeExtractorProcessor Processing: {}", employee);

        if (employee.getFirstName() == null || employee.getFirstName().length() == 0) {
            return null;
        }

        return employee;
    }

}