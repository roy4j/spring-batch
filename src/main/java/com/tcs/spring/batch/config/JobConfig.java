package com.tcs.spring.batch.config;

import com.tcs.spring.batch.listener.JobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@EnableBatchProcessing
@SpringBootApplication(scanBasePackages = {"com.tcs.spring.batch"})
@PropertySource({"classpath:/application.properties"})
public class JobConfig {

    @Bean
    public Job employeeJob(JobRepository jobRepository, Step employeeStep, JobListener listener) {
        return new JobBuilder("employeeJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(employeeStep)
                .build();
    }
}
