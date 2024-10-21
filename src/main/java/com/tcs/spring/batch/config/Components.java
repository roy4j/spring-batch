package com.tcs.spring.batch.config;

import com.tcs.spring.batch.domain.Employee;
import com.tcs.spring.batch.mapper.EmployeeRowMapper;
import com.tcs.spring.batch.processor.EmployeeExtractorProcessor;
import com.tcs.spring.batch.processor.EmployeeLoaderProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.HsqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class Components {

    @Value("${batch.page.size}")
    private int pageSize;

    @Value("${input.file.name}")
    private String inputFileName;

    @Value("${output.file.name}")
    private String outputFileName;

    @Value("${insert.sql}")
    private String insertSql;

    @Value("${query.select}")
    private String select;

    @Value("${query.from}")
    private String from;

    @Value("${query.where}")
    private String where;

    @Value("${query.sort}")
    private String orderBy;

    @Bean
    public FlatFileItemReader<Employee> employeeLoaderReader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeLoaderReader")
                .resource(new ClassPathResource(inputFileName))
                .delimited()
                .names("firstName", "lastName")
                .linesToSkip(1)
                .targetType(Employee.class)
                .build();
    }

    @Bean
    public EmployeeLoaderProcessor employeeLoaderProcessor() {
        return new EmployeeLoaderProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Employee> employeeLoaderWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Employee>()
                .sql(insertSql)
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Employee> employeeExtractorReader(DataSource dataSource, EmployeeRowMapper rowMapper) {
        return new JdbcPagingItemReaderBuilder<Employee>()
                .name("employeeExtractorReader")
                .dataSource(dataSource)
                .queryProvider(pagingQueryProvider())
                .pageSize(pageSize)
                .rowMapper(rowMapper)
                .build();
    }

    @Bean
    public HsqlPagingQueryProvider pagingQueryProvider() {
        HsqlPagingQueryProvider hsqlPagingQueryProvider = new HsqlPagingQueryProvider();
        hsqlPagingQueryProvider.setSelectClause(select);
        hsqlPagingQueryProvider.setFromClause(from);
        hsqlPagingQueryProvider.setWhereClause(where);
        hsqlPagingQueryProvider.getSortKeys().put(orderBy, Order.ASCENDING);
        return hsqlPagingQueryProvider;
    }

    @Bean
    public EmployeeExtractorProcessor employeeExtractorProcessor() {
        return new EmployeeExtractorProcessor();
    }

    @Bean
    public FlatFileItemWriter<Employee> employeeExtractorWriter() {
        return new FlatFileItemWriterBuilder<Employee>()
                .name("employeeExtractorWriter")
                .resource(new FileSystemResource(outputFileName))
                .lineAggregator(new PassThroughLineAggregator<>())
                .append(false)
                .shouldDeleteIfEmpty(false)
                .shouldDeleteIfExists(true)
                .build();
    }
}
