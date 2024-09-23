package com.tcs.spring.batch.config;

import com.tcs.spring.batch.domain.Employee;
import com.tcs.spring.batch.processor.EmployeeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class Components {

    @Value("${input.file.name}")
    private String inputFileName;

    @Value("${insert.sql}")
    private String insertSql;

    @Bean
    public FlatFileItemReader<Employee> reader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeReader")
                .resource(new ClassPathResource(inputFileName))
                .delimited()
                .names("firstName", "lastName")
                .linesToSkip(1)
                .targetType(Employee.class)
                .build();
    }

    @Bean
    public EmployeeProcessor processor() {
        return new EmployeeProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Employee> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Employee>()
                .sql(insertSql)
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }
}
