package com.tcs.spring.batch.tasklet;

import com.tcs.spring.batch.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggerTasklet implements Tasklet {

    @Value("${select.sql}")
    private String selectSql;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("--------------------------------------------");
        jdbcTemplate.query(selectSql, new DataClassRowMapper<>(Employee.class))
                .forEach(emp -> log.info("{}", emp));
        log.info("--------------------------------------------");
        return RepeatStatus.FINISHED;
    }
}
