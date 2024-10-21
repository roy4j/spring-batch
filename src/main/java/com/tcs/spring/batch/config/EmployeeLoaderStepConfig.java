package com.tcs.spring.batch.config;

import com.tcs.spring.batch.domain.Employee;
import com.tcs.spring.batch.listener.EmployeeChunkListener;
import com.tcs.spring.batch.processor.EmployeeLoaderProcessor;
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
public class EmployeeLoaderStepConfig {

    @Value("${batch.chunk.size}")
    private int chunkSize;

    @Bean
    public Step employeeLoaderStep(JobRepository jobRepository,
                             DataSourceTransactionManager transactionManager,
                             FlatFileItemReader<Employee> employeeLoaderReader,
                             EmployeeLoaderProcessor employeeLoaderProcessor,
                             JdbcBatchItemWriter<Employee> employeeLoaderWriter,
                             EmployeeChunkListener employeeChunkListener) {

        return new StepBuilder("employeeLoaderStep", jobRepository)
                .<Employee, Employee>chunk(chunkSize, transactionManager)
                .reader(employeeLoaderReader)
                .processor(employeeLoaderProcessor)
                .writer(employeeLoaderWriter)
                .listener(employeeChunkListener)
                .build();
    }
}
