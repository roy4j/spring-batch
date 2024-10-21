package com.tcs.spring.batch.config;

import com.tcs.spring.batch.domain.Employee;
import com.tcs.spring.batch.listener.EmployeeChunkListener;
import com.tcs.spring.batch.processor.EmployeeExtractorProcessor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class EmployeeExtractorStepConfig {

    @Value("${batch.chunk.size}")
    private int chunkSize;

    @Bean
    public Step employeeExtractorStep(JobRepository jobRepository,
                                      DataSourceTransactionManager transactionManager,
                                      JdbcPagingItemReader<Employee> employeeExtractorReader,
                                      EmployeeExtractorProcessor employeeExtractorProcessor,
                                      FlatFileItemWriter<Employee> employeeExtractorWriter,
                                      EmployeeChunkListener employeeChunkListener) {

        return new StepBuilder("employeeExtractorStep", jobRepository)
                .<Employee, Employee>chunk(chunkSize, transactionManager)
                .reader(employeeExtractorReader)
                .processor(employeeExtractorProcessor)
                .writer(employeeExtractorWriter)
                .listener(employeeChunkListener)
                .build();
    }
}
