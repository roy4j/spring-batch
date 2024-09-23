package com.tcs.spring.batch.listener;

import com.tcs.spring.batch.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobListener implements JobExecutionListener {

    @Value("${select.sql}")
    private String selectSql;

    private final JdbcTemplate jdbcTemplate;

    public JobListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            log.info("--------------------------------------------");
            jdbcTemplate.query(selectSql, new DataClassRowMapper<>(Employee.class))
                    .forEach(emp -> log.info("{}", emp));
            log.info("--------------------------------------------");
        }
    }
}
