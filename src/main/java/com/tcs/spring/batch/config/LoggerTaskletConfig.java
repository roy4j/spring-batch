package com.tcs.spring.batch.config;

import com.tcs.spring.batch.tasklet.LoggerTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class LoggerTaskletConfig {

    @Bean
    public Step loggerTaskletStep(DataSourceTransactionManager batchTransactionManager,
                                  LoggerTasklet loggerTasklet,
                                  JobRepository jobRepository) {
        return new StepBuilder("loggerTaskletStep", jobRepository)
                .tasklet(loggerTasklet, batchTransactionManager)
                .build();
    }
}
