package com.tcs.spring.batch.config;

import com.tcs.spring.batch.domain.Employee;
import com.tcs.spring.batch.processor.EmployeeProcessor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class StepConfig {

    @Value("${batch.chunk.size}")
    private int chunkSize;

    @Bean
    public Step employeeStep(JobRepository jobRepository,
                             DataSourceTransactionManager transactionManager,
                             FlatFileItemReader<Employee> reader,
                             EmployeeProcessor processor,
                             JdbcBatchItemWriter<Employee> writer) {

        return new StepBuilder("employeeStep", jobRepository)
                .<Employee, Employee>chunk(chunkSize, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
